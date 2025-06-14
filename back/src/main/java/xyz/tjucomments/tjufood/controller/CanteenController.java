package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.service.ICanteenService;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

@Tag(name = "3. 食堂管理", description = "食堂信息的增删改查接口")
@RestController
@RequestMapping("/api/canteens")
public class CanteenController {
    @Resource
    private ICanteenService canteenService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Operation(summary = "查询所有食堂列表")
    @GetMapping
    public Result listCanteens() {
        return canteenService.listCanteens();
    }

    @Operation(summary = "查询单个食堂详情")
    @GetMapping("/{id}")
    public Result getCanteenById(@Parameter(description = "食堂的唯一ID", required = true) @PathVariable("id") Long id) {
        return canteenService.queryCanteenById(id);
    }

    @Operation(summary = "新增食堂")
    @PostMapping
    public Result addCanteen(@RequestBody Canteen canteen) {
        boolean isSuccess = canteenService.save(canteen);
        return isSuccess ? Result.ok(canteen.getId()) : Result.fail("新增食堂失败！");
    }

    @Operation(summary = "修改食堂信息")
    @PutMapping
    public Result updateCanteen(@RequestBody Canteen canteen) {
        if (canteen.getId() == null) {
            return Result.fail("更新失败，食堂ID不能为空");
        }
        boolean isSuccess = canteenService.updateById(canteen);
        if(isSuccess) {
            stringRedisTemplate.delete(RedisConstants.CACHE_CANTEEN_KEY + canteen.getId());
        }
        return isSuccess ? Result.ok() : Result.fail("更新食堂失败！");
    }

    @Operation(summary = "删除食堂")
    @DeleteMapping("/{id}")
    public Result deleteCanteen(@Parameter(description = "食堂的唯一ID", required = true) @PathVariable("id") Long id) {
        boolean isSuccess = canteenService.removeById(id);
        if(isSuccess) {
            stringRedisTemplate.delete(RedisConstants.CACHE_CANTEEN_KEY + id);
        }
        return isSuccess ? Result.ok() : Result.fail("删除食堂失败！");
    }
}

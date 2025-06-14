package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.service.IStallService;

@Tag(name = "4. 窗口管理", description = "食堂窗口信息的增删改查接口")
@RestController
@RequestMapping("/api/stalls")
public class StallController {
    @Resource
    private IStallService stallService;

    @Operation(summary = "查询食堂下的所有窗口")
    @GetMapping("/canteen/{canteenId}")
    public Result getStallsByCanteenId(@Parameter(description = "所属食堂的ID", required = true) @PathVariable("canteenId") Long canteenId) {
        return stallService.listStallsByCanteenId(canteenId);
    }

    @Operation(summary = "查询单个窗口详情")
    @GetMapping("/{id}")
    public Result getStallById(@Parameter(description = "窗口的唯一ID", required = true) @PathVariable("id") Long id) {
        return stallService.queryStallById(id);
    }

    @Operation(summary = "新增窗口")
    @PostMapping
    public Result addStall(@RequestBody Stall stall) {
        boolean isSuccess = stallService.save(stall);
        return isSuccess ? Result.ok(stall.getId()) : Result.fail("新增窗口失败！");
    }

    @Operation(summary = "修改窗口信息")
    @PutMapping
    public Result updateStall(@RequestBody Stall stall) {
        if (stall.getId() == null) {
            return Result.fail("更新失败，窗口ID不能为空");
        }
        boolean isSuccess = stallService.updateById(stall);
        return isSuccess ? Result.ok() : Result.fail("更新窗口失败！");
    }

    @Operation(summary = "删除窗口")
    @DeleteMapping("/{id}")
    public Result deleteStall(@Parameter(description = "窗口的唯一ID", required = true) @PathVariable("id") Long id) {
        boolean isSuccess = stallService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail("删除窗口失败！");
    }
}

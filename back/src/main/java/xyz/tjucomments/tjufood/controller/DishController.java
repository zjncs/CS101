package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.service.IDishService;

@Tag(name = "5. 菜品管理", description = "菜品信息的增删改查及搜索接口")
@RestController
@RequestMapping("/api/dishes")
public class DishController {
    @Resource
    private IDishService dishService;

    @Operation(summary = "分页搜索菜品")
    @GetMapping("/search")
    public Result searchDishes(
            @Parameter(description = "菜品名称关键词") @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "当前页码") @RequestParam(value = "current", defaultValue = "1") Integer current,
            @Parameter(description = "每页数量") @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return dishService.searchDishesByName(name, current, size);
    }

    @Operation(summary = "查询窗口下的所有菜品")
    @GetMapping("/stall/{stallId}")
    public Result listDishesByStallId(@Parameter(description = "所属窗口的ID", required = true) @PathVariable Long stallId) {
        return dishService.listDishesByStallId(stallId);
    }

    @Operation(summary = "查询单个菜品详情")
    @GetMapping("/{id}")
    public Result getDishById(@Parameter(description = "菜品的唯一ID", required = true) @PathVariable Long id) {
        return dishService.queryDishById(id);
    }

    @Operation(summary = "新增菜品")
    @PostMapping
    public Result addDish(@RequestBody Dish dish) {
        boolean isSuccess = dishService.save(dish);
        return isSuccess ? Result.ok(dish.getId()) : Result.fail("新增菜品失败！");
    }

    @Operation(summary = "修改菜品信息")
    @PutMapping
    public Result updateDish(@RequestBody Dish dish) {
        if (dish.getId() == null) {
            return Result.fail("更新失败，菜品ID不能为空");
        }
        boolean isSuccess = dishService.updateById(dish);
        return isSuccess ? Result.ok() : Result.fail("更新菜品失败！");
    }

    @Operation(summary = "删除菜品")
    @DeleteMapping("/{id}")
    public Result deleteDish(@Parameter(description = "菜品的唯一ID", required = true) @PathVariable("id") Long id) {
        boolean isSuccess = dishService.removeById(id);
        return isSuccess ? Result.ok() : Result.fail("删除菜品失败！");
    }
}

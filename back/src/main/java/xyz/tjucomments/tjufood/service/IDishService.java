package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Dish;

// 继承 IService<Dish>
public interface IDishService extends IService<Dish> {
    // 保留返回 DTO 或包含特殊业务逻辑的方法
    Result queryDishById(Long id);
    Result listDishesByStallId(Long stallId);
    Result searchDishesByName(String name, Integer current, Integer size);

    // 以下方法已由 IService 提供，但为了Controller层代码兼容性，可以选择暂时保留
    Result addDish(Dish dish);
    Result updateDish(Dish dish);
    Result deleteDish(Long id);
}

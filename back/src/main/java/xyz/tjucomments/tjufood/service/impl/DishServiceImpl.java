package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.DishDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.mapper.DishMapper;
import xyz.tjucomments.tjufood.service.IDishService;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
    // baseMapper (即 DishMapper) 已由 ServiceImpl 自动注入

    @Override
    public Result queryDishById(Long id) {
        // 调用自定义XML查询，保持不变
        DishDTO dish = baseMapper.queryDishById(id);
        if(dish == null) {
            return Result.fail("菜品不存在！");
        }
        return Result.ok(dish);
    }

    @Override
    public Result listDishesByStallId(Long stallId) {
        // 调用自定义XML查询，保持不变
        List<DishDTO> dishes = baseMapper.listDishesByStallId(stallId);
        return Result.ok(dishes);
    }

    /**
     * 【分页重构】使用 MyBatis-Plus 的分页插件
     */
    @Override
    public Result searchDishesByName(String name, Integer current, Integer size) {
        // 【注意】此实现依赖于你已在项目中配置了 MybatisPlusInterceptor 和 PaginationInnerInterceptor
        // 并且需要从项目中移除 PageHelper 的依赖，以避免冲突。

        // 1. 创建 MyBatis-Plus 的 Page 对象
        Page<DishDTO> page = new Page<>(current, size);

        // 2. 调用 Mapper 方法进行分页查询
        // MP的分页插件会自动拦截这个查询，并追加分页SQL。
        // Mapper的XML文件不需要改动！
        List<DishDTO> dishes = baseMapper.searchDishesByName(page, name);

        // 3. 封装返回结果
        return Result.ok(dishes, page.getTotal());
    }

    @Override
    @Transactional
    public Result addDish(Dish dish) {
        boolean isSuccess = save(dish);
        return isSuccess ? Result.ok("新增菜品成功，ID为：" + dish.getId()) : Result.fail("新增菜品失败！");
    }

    @Override
    @Transactional
    public Result updateDish(Dish dish) {
        if (dish.getId() == null) {
            return Result.fail("更新失败，菜品ID不能为空");
        }
        boolean isSuccess = updateById(dish);
        return isSuccess ? Result.ok("更新菜品成功") : Result.fail("更新菜品失败！");
    }

    @Override
    @Transactional
    public Result deleteDish(Long id) {
        boolean isSuccess = removeById(id);
        return isSuccess ? Result.ok("删除菜品成功") : Result.fail("删除菜品失败！");
    }
}
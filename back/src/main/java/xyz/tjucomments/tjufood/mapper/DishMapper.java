package xyz.tjucomments.tjufood.mapper;

// 导入 Page 类
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.dto.DishDTO;
import java.util.List;

/**
 * 菜品Mapper接口 (已重写并适配 MyBatis-Plus)
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    // ========== 保留的自定义查询方法 ==========

    /**
     * 根据ID查询菜品详情 (返回DTO)
     */
    DishDTO queryDishById(Long id);

    /**
     * 根据窗口ID查询其下所有菜品 (返回DTO列表)
     */
    List<DishDTO> listDishesByStallId(@Param("stallId") Long stallId);

    /**
     * 【已修改】根据菜品名称模糊搜索菜品 (返回DTO列表，并支持分页)
     * * @param page 分页对象，必须作为第一个参数
     * @param name 菜品名称关键词
     * @return 分页后的菜品DTO列表
     */
    List<DishDTO> searchDishesByName(Page<DishDTO> page, @Param("name") String name);
}

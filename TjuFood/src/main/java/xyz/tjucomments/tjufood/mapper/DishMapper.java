package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Dish;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select * from tb_dish where stall_id = #{stallId}")
    List<Dish> findByStall(long stallId);

    @Select("select * from tb_dish where id = #{id}")
    Dish findById(long id);
}

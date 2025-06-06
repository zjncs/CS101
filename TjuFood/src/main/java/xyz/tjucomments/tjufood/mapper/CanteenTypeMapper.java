package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.CanteenType;

import java.util.List;

@Mapper
public interface CanteenTypeMapper {
    @Select("select * from tb_canteen_type order by sort")
    List<CanteenType> findAll();

    @Select("select * from tb_canteen_type where id = #{id}")
    CanteenType findById(long id);
}
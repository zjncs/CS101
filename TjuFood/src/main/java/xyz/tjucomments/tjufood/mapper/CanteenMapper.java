package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Canteen;

import java.util.List;

@Mapper
public interface CanteenMapper {
    @Select("select * from tb_canteen where campus_id = #{campusId}")
    List<Canteen> findByCampus(long campusId);

    @Select("select * from tb_canteen where id = #{id}")
    Canteen findById(long id);
}

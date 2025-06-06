package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Campus;

import java.util.List;

@Mapper
public interface CampusMapper {
    @Select("select * from tb_campus order by sort")
    List<Campus> findAll();

    @Select("select * from tb_campus where id = #{id}")
    Campus findById(long id);
}
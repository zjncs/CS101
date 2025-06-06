package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Medal;

import java.util.List;

@Mapper
public interface MedalMapper {
    @Select("select * from tb_medal order by id")
    List<Medal> findAll();

    @Select("select * from tb_medal where id = #{id}")
    Medal findById(long id);
}

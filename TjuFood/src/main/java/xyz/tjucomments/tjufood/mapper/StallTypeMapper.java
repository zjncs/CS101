package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.StallType;

import java.util.List;

@Mapper
public interface StallTypeMapper {
    @Select("select * from tb_stall_type order by sort")
    List<StallType> findAll();

    @Select("select * from tb_stall_type where id = #{id}")
    StallType findById(long id);
}

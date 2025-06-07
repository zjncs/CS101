package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Version;

import java.util.List;

@Mapper
public interface VersionMapper {
    @Select("select * from tb_version where status = 1 order by create_time desc")
    List<Version> listPublished();

    @Select("select * from tb_version where id = #{id}")
    Version findById(long id);
}
package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.File;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from tb_file order by create_time desc limit 20")
    List<File> latestFiles();

    @Select("select * from tb_file where id = #{id}")
    File findById(long id);
}
package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Config;

import java.util.List;

@Mapper
public interface ConfigMapper {
    @Select("select * from tb_config")
    List<Config> findAll();

    @Select("select * from tb_config where `key` = #{key}")
    Config findByKey(String key);
}
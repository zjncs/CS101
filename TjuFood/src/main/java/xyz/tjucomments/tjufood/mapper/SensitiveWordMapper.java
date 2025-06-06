package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.SensitiveWord;

import java.util.List;

@Mapper
public interface SensitiveWordMapper {
    @Select("select * from tb_sensitive_word order by create_time desc")
    List<SensitiveWord> findAll();

    @Select("select * from tb_sensitive_word where id = #{id}")
    SensitiveWord findById(long id);
}

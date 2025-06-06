package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Topic;

import java.util.List;

@Mapper
public interface TopicMapper {
    @Select("select * from tb_topic order by sort")
    List<Topic> findAll();

    @Select("select * from tb_topic where id = #{id}")
    Topic findById(long id);
}

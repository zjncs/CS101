package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Message;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("select * from tb_message where to_user_id = #{userId} order by create_time desc limit 20")
    List<Message> findByUser(long userId);
}

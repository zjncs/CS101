package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Feedback;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    @Select("select * from tb_feedback order by create_time desc limit 20")
    List<Feedback> latestFeedback();

    @Select("select * from tb_feedback where id = #{id}")
    Feedback findById(long id);
}
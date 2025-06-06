package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from tb_comment where blog_id = #{blogId} and parent_id is null order by create_time desc")
    List<Comment> findByBlog(long blogId);

    @Select("select * from tb_comment where review_id = #{reviewId} and parent_id is null order by create_time desc")
    List<Comment> findByReview(long reviewId);
}
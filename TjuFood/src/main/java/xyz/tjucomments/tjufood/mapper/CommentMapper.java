package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.*;
import xyz.tjucomments.tjufood.entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from tb_comment where blog_id = #{blogId} and parent_id is null order by create_time desc")
    List<Comment> findByBlog(long blogId);

    @Select("select * from tb_comment where review_id = #{reviewId} and parent_id is null order by create_time desc")
    List<Comment> findByReview(long reviewId);

    @Insert("insert into tb_comment(user_id, blog_id, review_id, parent_id, content, liked, status, create_time) " +
            "values(#{userId}, #{blogId}, #{reviewId}, #{parentId}, #{content}, #{liked}, #{status}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Comment comment);
}
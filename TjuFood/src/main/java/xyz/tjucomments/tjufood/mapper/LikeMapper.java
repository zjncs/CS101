package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.*;
import xyz.tjucomments.tjufood.entity.Like;

import java.util.List;

@Mapper
public interface LikeMapper {
    @Select("select * from tb_like where user_id = #{userId}")
    List<Like> findByUser(long userId);

    @Select("select * from tb_like where liked_id = #{likedId} and type = #{type}")
    List<Like> findByTarget(long likedId, int type);

    @Insert("insert into tb_like(user_id, liked_id, type, create_time) " +
            "values(#{userId}, #{likedId}, #{type}, now())")
    int insert(Like like);

    @Delete("delete from tb_like where user_id=#{userId} and liked_id=#{likedId} and type=#{type}")
    int delete(@Param("userId") long userId, @Param("likedId") long likedId, @Param("type") int type);
}
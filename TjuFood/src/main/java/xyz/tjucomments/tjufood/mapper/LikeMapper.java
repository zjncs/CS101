package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Like;

import java.util.List;

@Mapper
public interface LikeMapper {
    @Select("select * from tb_like where user_id = #{userId}")
    List<Like> findByUser(long userId);

    @Select("select * from tb_like where liked_id = #{likedId} and type = #{type}")
    List<Like> findByTarget(long likedId, int type);
}
package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Follow;

import java.util.List;

@Mapper
public interface FollowMapper {
    @Select("select * from tb_follow where user_id = #{userId}")
    List<Follow> findByUser(long userId);
}
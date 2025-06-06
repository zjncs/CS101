package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.*;
import xyz.tjucomments.tjufood.entity.Follow;

import java.util.List;

@Mapper
public interface FollowMapper {
    @Select("select * from tb_follow where user_id = #{userId}")
    List<Follow> findByUser(long userId);

    @Insert("insert into tb_follow(user_id, follow_user_id, create_time) " +
            "values(#{userId}, #{followUserId}, now())")
    int insert(Follow follow);

    @Delete("delete from tb_follow where user_id=#{userId} and follow_user_id=#{followUserId}")
    int delete(@Param("userId") long userId, @Param("followUserId") long followUserId);
}

package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.UserActivity;

import java.util.List;

@Mapper
public interface UserActivityMapper {
    @Select("select * from tb_user_activity where user_id = #{userId} order by join_time desc")
    List<UserActivity> findByUser(long userId);
}
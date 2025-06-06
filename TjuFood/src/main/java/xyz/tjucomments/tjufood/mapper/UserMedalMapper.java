package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.UserMedal;

import java.util.List;

@Mapper
public interface UserMedalMapper {
    @Select("select * from tb_user_medal where user_id = #{userId} order by obtain_time desc")
    List<UserMedal> findByUser(long userId);
}
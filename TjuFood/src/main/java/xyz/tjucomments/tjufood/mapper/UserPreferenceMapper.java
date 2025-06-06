package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.UserPreference;

import java.util.List;

@Mapper
public interface UserPreferenceMapper {
    @Select("select * from tb_user_preference where user_id = #{userId}")
    List<UserPreference> findByUser(long userId);
}

package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Favorite;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("select * from tb_favorite where user_id = #{userId}")
    List<Favorite> findByUser(long userId);
}

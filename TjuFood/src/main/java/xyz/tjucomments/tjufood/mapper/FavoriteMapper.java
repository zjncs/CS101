package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.*;
import xyz.tjucomments.tjufood.entity.Favorite;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    @Select("select * from tb_favorite where user_id = #{userId}")
    List<Favorite> findByUser(long userId);

    @Insert("insert into tb_favorite(user_id, favorite_id, type, create_time) " +
            "values(#{userId}, #{favoriteId}, #{type}, now())")
    int insert(Favorite favorite);

    @Delete("delete from tb_favorite where user_id=#{userId} and favorite_id=#{favoriteId} and type=#{type}")
    int delete(@Param("userId") long userId, @Param("favoriteId") long favoriteId, @Param("type") int type);
}

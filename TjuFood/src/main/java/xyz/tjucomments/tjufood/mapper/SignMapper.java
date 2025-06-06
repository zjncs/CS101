package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Sign;

import java.util.List;

@Mapper
public interface SignMapper {
    @Select("select * from tb_sign where user_id = #{userId} order by date desc")
    List<Sign> findByUser(long userId);
}

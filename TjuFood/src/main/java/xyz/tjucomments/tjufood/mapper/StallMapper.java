package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Stall;

import java.util.List;

@Mapper
public interface StallMapper {
    @Select("select * from tb_stall where canteen_id = #{canteenId}")
    List<Stall> findByCanteen(long canteenId);

    @Select("select * from tb_stall where id = #{id}")
    Stall findById(long id);
}

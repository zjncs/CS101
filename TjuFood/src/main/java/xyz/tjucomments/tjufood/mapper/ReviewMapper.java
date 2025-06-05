package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("select * from tb_review where dish_id = #{dishId} order by create_time desc")
    List<Review> findByDish(long dishId);

    @Select("select * from tb_review where id = #{id}")
    Review findById(long id);
}

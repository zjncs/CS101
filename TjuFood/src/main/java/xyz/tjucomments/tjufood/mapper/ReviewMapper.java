package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.*;
import xyz.tjucomments.tjufood.entity.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("select * from tb_review where dish_id = #{dishId} order by create_time desc")
    List<Review> findByDish(long dishId);

    @Select("select * from tb_review where id = #{id}")
    Review findById(long id);

    @Insert("insert into tb_review(user_id, content, images, canteen_id, stall_id, dish_id, overall_score, taste_score, environment_score, service_score, price_score, liked, status, create_time) " +
            "values(#{userId}, #{content}, #{images}, #{canteenId}, #{stallId}, #{dishId}, #{overallScore}, #{tasteScore}, #{environmentScore}, #{serviceScore}, #{priceScore}, #{liked}, #{status}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Review review);
}
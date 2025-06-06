package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.ImageCategory;

import java.util.List;

@Mapper
public interface ImageCategoryMapper {
    @Select("select * from tb_image_category order by sort")
    List<ImageCategory> findAll();

    @Select("select * from tb_image_category where id = #{id}")
    ImageCategory findById(long id);
}

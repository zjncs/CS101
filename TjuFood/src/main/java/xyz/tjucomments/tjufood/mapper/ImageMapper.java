package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Image;

import java.util.List;

@Mapper
public interface ImageMapper {
    @Select("select * from tb_image where status = 0 order by create_time desc limit 20")
    List<Image> latestImages();

    @Select("select * from tb_image where id = #{id}")
    Image findById(long id);
}
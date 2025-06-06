package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Banner;

import java.util.List;

@Mapper
public interface BannerMapper {
    @Select("select * from tb_banner where status = 0 order by sort")
    List<Banner> activeBanners();

    @Select("select * from tb_banner where id = #{id}")
    Banner findById(long id);
}

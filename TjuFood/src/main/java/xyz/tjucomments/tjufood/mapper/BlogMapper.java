package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Blog;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Select("select * from tb_blog order by create_time desc limit 20")
    List<Blog> latestBlogs();

    @Select("select * from tb_blog where id = #{id}")
    Blog findById(long id);
}
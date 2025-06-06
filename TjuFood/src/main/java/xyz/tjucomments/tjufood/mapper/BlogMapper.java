package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import xyz.tjucomments.tjufood.entity.Blog;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Select("select * from tb_blog order by create_time desc limit 20")
    List<Blog> latestBlogs();

    @Select("select * from tb_blog where id = #{id}")
    Blog findById(long id);

    @Insert("insert into tb_blog(user_id,title,content,images,canteen_id,stall_id,liked,comments,status,is_top,create_time) " +
            "values(#{userId},#{title},#{content},#{images},#{canteenId},#{stallId},#{liked},#{comments},#{status},#{isTop},now())")
    int insert(Blog blog);
}

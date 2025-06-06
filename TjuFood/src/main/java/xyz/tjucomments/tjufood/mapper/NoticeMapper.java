package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Notice;

import java.util.List;

@Mapper
public interface NoticeMapper {
    @Select("select * from tb_notice where status = 1 order by create_time desc limit 20")
    List<Notice> latestNotices();

    @Select("select * from tb_notice where id = #{id}")
    Notice findById(long id);
}
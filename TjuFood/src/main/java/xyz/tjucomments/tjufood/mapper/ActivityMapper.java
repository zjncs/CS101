package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Activity;

import java.util.List;

@Mapper
public interface ActivityMapper {
    @Select("select * from tb_activity where status in (0,1) order by start_time desc")
    List<Activity> listOngoing();

    @Select("select * from tb_activity where id = #{id}")
    Activity findById(long id);
}
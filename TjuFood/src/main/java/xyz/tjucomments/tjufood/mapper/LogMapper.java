package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Log;

import java.util.List;

@Mapper
public interface LogMapper {
    @Select("select * from tb_log order by create_time desc limit 20")
    List<Log> latestLogs();

    @Select("select * from tb_log where id = #{id}")
    Log findById(long id);
}
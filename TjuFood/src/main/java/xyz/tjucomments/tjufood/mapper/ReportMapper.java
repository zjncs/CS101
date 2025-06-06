package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Report;

import java.util.List;

@Mapper
public interface ReportMapper {
    @Select("select * from tb_report order by create_time desc limit 20")
    List<Report> latestReport();

    @Select("select * from tb_report where id = #{id}")
    Report findById(long id);
}

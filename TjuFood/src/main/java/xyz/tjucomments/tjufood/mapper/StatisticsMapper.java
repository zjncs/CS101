package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Statistics;

import java.util.List;

@Mapper
public interface StatisticsMapper {
    @Select("select * from tb_statistics where type = #{type} and period = #{period} order by date desc limit 30")
    List<Statistics> listStatistics(int type, String period);
}

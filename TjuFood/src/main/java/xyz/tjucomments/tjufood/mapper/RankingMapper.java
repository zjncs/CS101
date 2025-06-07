package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Ranking;

import java.util.List;

@Mapper
public interface RankingMapper {
    @Select("select * from tb_ranking where type = #{type} order by rank limit 10")
    List<Ranking> topRanking(int type);
}
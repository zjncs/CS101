package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.LevelRule;

import java.util.List;

@Mapper
public interface LevelRuleMapper {
    @Select("select * from tb_level_rule order by level")
    List<LevelRule> findAll();
}
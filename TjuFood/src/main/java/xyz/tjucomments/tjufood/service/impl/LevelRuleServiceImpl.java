package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.LevelRule;
import xyz.tjucomments.tjufood.mapper.LevelRuleMapper;
import xyz.tjucomments.tjufood.service.LevelRuleService;

import java.util.List;

@Service
public class LevelRuleServiceImpl implements LevelRuleService {

    @Resource
    LevelRuleMapper mapper;

    @Override
    public List<LevelRule> listAll() {
        return mapper.findAll();
    }
}

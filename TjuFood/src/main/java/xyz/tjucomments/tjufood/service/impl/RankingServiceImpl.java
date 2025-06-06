package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Ranking;
import xyz.tjucomments.tjufood.mapper.RankingMapper;
import xyz.tjucomments.tjufood.service.RankingService;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

    @Resource
    RankingMapper mapper;

    @Override
    public List<Ranking> topRanking(int type) {
        return mapper.topRanking(type);
    }
}
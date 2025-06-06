package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Statistics;
import xyz.tjucomments.tjufood.mapper.StatisticsMapper;
import xyz.tjucomments.tjufood.service.StatisticsService;

import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    StatisticsMapper mapper;

    @Override
    public List<Statistics> listStatistics(int type, String period) {
        return mapper.listStatistics(type, period);
    }
}
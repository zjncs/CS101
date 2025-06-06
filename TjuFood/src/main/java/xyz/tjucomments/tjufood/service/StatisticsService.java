package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Statistics;

import java.util.List;

public interface StatisticsService {
    List<Statistics> listStatistics(int type, String period);
}
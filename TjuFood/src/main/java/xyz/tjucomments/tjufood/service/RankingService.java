package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Ranking;

import java.util.List;

public interface RankingService {
    List<Ranking> topRanking(int type);
}

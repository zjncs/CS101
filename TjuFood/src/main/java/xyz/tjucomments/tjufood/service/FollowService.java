package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Follow;

import java.util.List;

public interface FollowService {
    List<Follow> listByUser(long userId);
}
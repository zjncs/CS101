package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Like;

import java.util.List;

public interface LikeService {
    List<Like> listByUser(long userId);
    List<Like> listByTarget(long likedId, int type);
    boolean addLike(Like like);
    boolean removeLike(long userId, long likedId, int type);
}
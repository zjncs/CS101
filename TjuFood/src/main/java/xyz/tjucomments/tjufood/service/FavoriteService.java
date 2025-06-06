package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    List<Favorite> listByUser(long userId);
    boolean addFavorite(Favorite favorite);
    boolean removeFavorite(long userId, long favoriteId, int type);
}

package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Favorite;
import xyz.tjucomments.tjufood.mapper.FavoriteMapper;
import xyz.tjucomments.tjufood.service.FavoriteService;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    FavoriteMapper mapper;

    @Override
    public List<Favorite> listByUser(long userId) {
        return mapper.findByUser(userId);
    }

    @Override
    public boolean addFavorite(Favorite favorite) {
        return mapper.insert(favorite) > 0;
    }

    @Override
    public boolean removeFavorite(long userId, long favoriteId, int type) {
        return mapper.delete(userId, favoriteId, type) > 0;
    }
}
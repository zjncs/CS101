package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Like;
import xyz.tjucomments.tjufood.mapper.LikeMapper;
import xyz.tjucomments.tjufood.service.LikeService;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    LikeMapper mapper;

    @Override
    public List<Like> listByUser(long userId) {
        return mapper.findByUser(userId);
    }

    @Override
    public List<Like> listByTarget(long likedId, int type) {
        return mapper.findByTarget(likedId, type);
    }
}

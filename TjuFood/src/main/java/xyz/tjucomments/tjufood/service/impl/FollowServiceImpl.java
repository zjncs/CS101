package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Follow;
import xyz.tjucomments.tjufood.mapper.FollowMapper;
import xyz.tjucomments.tjufood.service.FollowService;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    FollowMapper mapper;

    @Override
    public List<Follow> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}
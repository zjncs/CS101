package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.UserActivity;
import xyz.tjucomments.tjufood.mapper.UserActivityMapper;
import xyz.tjucomments.tjufood.service.UserActivityService;

import java.util.List;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Resource
    UserActivityMapper mapper;

    @Override
    public List<UserActivity> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}

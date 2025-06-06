package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.UserMedal;
import xyz.tjucomments.tjufood.mapper.UserMedalMapper;
import xyz.tjucomments.tjufood.service.UserMedalService;

import java.util.List;

@Service
public class UserMedalServiceImpl implements UserMedalService {

    @Resource
    UserMedalMapper mapper;

    @Override
    public List<UserMedal> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}
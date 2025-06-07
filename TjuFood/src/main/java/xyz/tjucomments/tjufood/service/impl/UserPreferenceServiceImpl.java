package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.UserPreference;
import xyz.tjucomments.tjufood.mapper.UserPreferenceMapper;
import xyz.tjucomments.tjufood.service.UserPreferenceService;

import java.util.List;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Resource
    UserPreferenceMapper mapper;

    @Override
    public List<UserPreference> findByUser(long userId) {
        return mapper.findByUser(userId);
    }
}
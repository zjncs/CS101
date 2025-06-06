package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Activity;
import xyz.tjucomments.tjufood.mapper.ActivityMapper;
import xyz.tjucomments.tjufood.service.ActivityService;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    ActivityMapper mapper;

    @Override
    public List<Activity> listOngoing() {
        return mapper.listOngoing();
    }

    @Override
    public Activity getById(long id) {
        return mapper.findById(id);
    }
}
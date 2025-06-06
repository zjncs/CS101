package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Topic;
import xyz.tjucomments.tjufood.mapper.TopicMapper;
import xyz.tjucomments.tjufood.service.TopicService;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    TopicMapper mapper;

    @Override
    public List<Topic> listAll() {
        return mapper.findAll();
    }

    @Override
    public Topic getById(long id) {
        return mapper.findById(id);
    }
}
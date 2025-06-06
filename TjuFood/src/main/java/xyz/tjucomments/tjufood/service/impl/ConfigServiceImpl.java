package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Config;
import xyz.tjucomments.tjufood.mapper.ConfigMapper;
import xyz.tjucomments.tjufood.service.ConfigService;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    ConfigMapper mapper;

    @Override
    public List<Config> listAll() {
        return mapper.findAll();
    }

    @Override
    public Config getByKey(String key) {
        return mapper.findByKey(key);
    }
}
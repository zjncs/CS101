package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Medal;
import xyz.tjucomments.tjufood.mapper.MedalMapper;
import xyz.tjucomments.tjufood.service.MedalService;

import java.util.List;

@Service
public class MedalServiceImpl implements MedalService {

    @Resource
    MedalMapper mapper;

    @Override
    public List<Medal> listAll() {
        return mapper.findAll();
    }

    @Override
    public Medal getById(long id) {
        return mapper.findById(id);
    }
}
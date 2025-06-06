package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Campus;
import xyz.tjucomments.tjufood.mapper.CampusMapper;
import xyz.tjucomments.tjufood.service.CampusService;

import java.util.List;

@Service
public class CampusServiceImpl implements CampusService {

    @Resource
    CampusMapper mapper;

    @Override
    public List<Campus> listAll() {
        return mapper.findAll();
    }

    @Override
    public Campus getById(long id) {
        return mapper.findById(id);
    }
}
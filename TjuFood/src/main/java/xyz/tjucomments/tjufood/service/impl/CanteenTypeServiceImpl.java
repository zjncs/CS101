package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.CanteenType;
import xyz.tjucomments.tjufood.mapper.CanteenTypeMapper;
import xyz.tjucomments.tjufood.service.CanteenTypeService;

import java.util.List;

@Service
public class CanteenTypeServiceImpl implements CanteenTypeService {

    @Resource
    CanteenTypeMapper mapper;

    @Override
    public List<CanteenType> listAll() {
        return mapper.findAll();
    }

    @Override
    public CanteenType getById(long id) {
        return mapper.findById(id);
    }
}
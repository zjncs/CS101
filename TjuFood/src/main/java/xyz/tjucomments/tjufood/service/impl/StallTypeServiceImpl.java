package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.StallType;
import xyz.tjucomments.tjufood.mapper.StallTypeMapper;
import xyz.tjucomments.tjufood.service.StallTypeService;

import java.util.List;

@Service
public class StallTypeServiceImpl implements StallTypeService {

    @Resource
    StallTypeMapper mapper;

    @Override
    public List<StallType> listAll() {
        return mapper.findAll();
    }

    @Override
    public StallType getById(long id) {
        return mapper.findById(id);
    }
}

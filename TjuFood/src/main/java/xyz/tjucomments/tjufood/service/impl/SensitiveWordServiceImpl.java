package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.SensitiveWord;
import xyz.tjucomments.tjufood.mapper.SensitiveWordMapper;
import xyz.tjucomments.tjufood.service.SensitiveWordService;

import java.util.List;

@Service
public class SensitiveWordServiceImpl implements SensitiveWordService {

    @Resource
    SensitiveWordMapper mapper;

    @Override
    public List<SensitiveWord> listAll() {
        return mapper.findAll();
    }

    @Override
    public SensitiveWord getById(long id) {
        return mapper.findById(id);
    }
}

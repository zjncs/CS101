package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Log;
import xyz.tjucomments.tjufood.mapper.LogMapper;
import xyz.tjucomments.tjufood.service.LogService;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper mapper;

    @Override
    public List<Log> latest() {
        return mapper.latestLogs();
    }

    @Override
    public Log getById(long id) {
        return mapper.findById(id);
    }
}
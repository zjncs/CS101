package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Version;
import xyz.tjucomments.tjufood.mapper.VersionMapper;
import xyz.tjucomments.tjufood.service.VersionService;

import java.util.List;

@Service
public class VersionServiceImpl implements VersionService {

    @Resource
    VersionMapper mapper;

    @Override
    public List<Version> listPublished() {
        return mapper.listPublished();
    }

    @Override
    public Version getById(long id) {
        return mapper.findById(id);
    }
}
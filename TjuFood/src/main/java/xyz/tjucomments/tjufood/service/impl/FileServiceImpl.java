package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.File;
import xyz.tjucomments.tjufood.mapper.FileMapper;
import xyz.tjucomments.tjufood.service.FileService;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    FileMapper mapper;

    @Override
    public List<File> latest() {
        return mapper.latestFiles();
    }

    @Override
    public File getById(long id) {
        return mapper.findById(id);
    }
}
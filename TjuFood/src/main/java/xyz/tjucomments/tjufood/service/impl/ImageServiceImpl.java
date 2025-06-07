package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Image;
import xyz.tjucomments.tjufood.mapper.ImageMapper;
import xyz.tjucomments.tjufood.service.ImageService;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    ImageMapper mapper;

    @Override
    public List<Image> latest() {
        return mapper.latestImages();
    }

    @Override
    public Image getById(long id) {
        return mapper.findById(id);
    }
}
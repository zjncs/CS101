package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.ImageCategory;
import xyz.tjucomments.tjufood.mapper.ImageCategoryMapper;
import xyz.tjucomments.tjufood.service.ImageCategoryService;

import java.util.List;

@Service
public class ImageCategoryServiceImpl implements ImageCategoryService {

    @Resource
    ImageCategoryMapper mapper;

    @Override
    public List<ImageCategory> listAll() {
        return mapper.findAll();
    }

    @Override
    public ImageCategory getById(long id) {
        return mapper.findById(id);
    }
}

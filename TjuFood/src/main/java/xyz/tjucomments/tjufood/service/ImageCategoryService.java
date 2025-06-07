package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.ImageCategory;

import java.util.List;

public interface ImageCategoryService {
    List<ImageCategory> listAll();
    ImageCategory getById(long id);
}
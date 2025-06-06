package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Image;

import java.util.List;

public interface ImageService {
    List<Image> latest();
    Image getById(long id);
}

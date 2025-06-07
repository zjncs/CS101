package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.CanteenType;

import java.util.List;

public interface CanteenTypeService {
    List<CanteenType> listAll();
    CanteenType getById(long id);
}
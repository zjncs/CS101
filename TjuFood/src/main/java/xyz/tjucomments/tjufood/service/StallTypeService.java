package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.StallType;

import java.util.List;

public interface StallTypeService {
    List<StallType> listAll();
    StallType getById(long id);
}

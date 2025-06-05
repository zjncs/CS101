package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Campus;

import java.util.List;

public interface CampusService {
    List<Campus> listAll();
    Campus getById(long id);
}

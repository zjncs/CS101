package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Log;

import java.util.List;

public interface LogService {
    List<Log> latest();
    Log getById(long id);
}

package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Medal;

import java.util.List;

public interface MedalService {
    List<Medal> listAll();
    Medal getById(long id);
}
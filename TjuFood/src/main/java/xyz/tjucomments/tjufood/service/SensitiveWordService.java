package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.SensitiveWord;

import java.util.List;

public interface SensitiveWordService {
    List<SensitiveWord> listAll();
    SensitiveWord getById(long id);
}

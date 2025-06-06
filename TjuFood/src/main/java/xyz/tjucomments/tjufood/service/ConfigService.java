package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Config;

import java.util.List;

public interface ConfigService {
    List<Config> listAll();
    Config getByKey(String key);
}

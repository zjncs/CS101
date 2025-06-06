package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> listAll();
    Topic getById(long id);
}

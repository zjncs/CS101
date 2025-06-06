package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> listByUser(long userId);
}

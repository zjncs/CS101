package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Sign;

import java.util.List;

public interface SignService {
    List<Sign> listByUser(long userId);
}
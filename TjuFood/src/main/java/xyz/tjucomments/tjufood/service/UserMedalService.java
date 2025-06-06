package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.UserMedal;

import java.util.List;

public interface UserMedalService {
    List<UserMedal> listByUser(long userId);
}

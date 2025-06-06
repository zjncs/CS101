package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.UserActivity;

import java.util.List;

public interface UserActivityService {
    List<UserActivity> listByUser(long userId);
}
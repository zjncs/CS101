package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Activity;

import java.util.List;

public interface ActivityService {
    List<Activity> listOngoing();
    Activity getById(long id);
}

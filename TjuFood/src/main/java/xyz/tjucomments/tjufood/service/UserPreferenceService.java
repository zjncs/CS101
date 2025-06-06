package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.UserPreference;

import java.util.List;

public interface UserPreferenceService {
    List<UserPreference> findByUser(long userId);
}

package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Version;

import java.util.List;

public interface VersionService {
    List<Version> listPublished();
    Version getById(long id);
}

package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.File;

import java.util.List;

public interface FileService {
    List<File> latest();
    File getById(long id);
}
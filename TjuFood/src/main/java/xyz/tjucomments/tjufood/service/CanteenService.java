package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Canteen;

import java.util.List;

public interface CanteenService {
    List<Canteen> listByCampus(long campusId);
    Canteen getById(long id);
}

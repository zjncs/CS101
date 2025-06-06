package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Stall;

import java.util.List;

public interface StallService {
    List<Stall> listByCanteen(long canteenId);
    Stall getById(long id);
}

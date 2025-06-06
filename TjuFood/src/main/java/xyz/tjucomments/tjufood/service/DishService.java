package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Dish;

import java.util.List;

public interface DishService {
    List<Dish> listByStall(long stallId);
    Dish getById(long id);
}

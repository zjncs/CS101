package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.mapper.DishMapper;
import xyz.tjucomments.tjufood.service.DishService;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Resource
    DishMapper mapper;

    @Override
    public List<Dish> listByStall(long stallId) {
        return mapper.findByStall(stallId);
    }

    @Override
    public Dish getById(long id) {
        return mapper.findById(id);
    }
}

package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Dish;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.DishService;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Resource
    DishService service;

    @GetMapping("/stall/{stallId}")
    public RestBean<List<Dish>> listByStall(@PathVariable long stallId) {
        return RestBean.success(service.listByStall(stallId));
    }

    @GetMapping("/{id}")
    public RestBean<Dish> getDish(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

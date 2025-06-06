package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Favorite;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Resource
    FavoriteService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Favorite>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}

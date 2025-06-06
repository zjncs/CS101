package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Like;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Resource
    LikeService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Like>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }

    @GetMapping("/target/{type}/{id}")
    public RestBean<List<Like>> listByTarget(@PathVariable int type, @PathVariable("id") long likedId) {
        return RestBean.success(service.listByTarget(likedId, type));
    }
}
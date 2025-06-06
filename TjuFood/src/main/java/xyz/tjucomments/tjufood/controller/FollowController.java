package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Follow;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Resource
    FollowService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Follow>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}
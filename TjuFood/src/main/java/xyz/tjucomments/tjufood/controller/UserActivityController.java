package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.UserActivity;
import xyz.tjucomments.tjufood.service.UserActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/user/activity")
public class UserActivityController {

    @Resource
    UserActivityService service;

    @GetMapping("/{userId}")
    public RestBean<List<UserActivity>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}
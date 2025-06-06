package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.UserMedal;
import xyz.tjucomments.tjufood.service.UserMedalService;

import java.util.List;

@RestController
@RequestMapping("/api/user/medal")
public class UserMedalController {

    @Resource
    UserMedalService service;

    @GetMapping("/{userId}")
    public RestBean<List<UserMedal>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}
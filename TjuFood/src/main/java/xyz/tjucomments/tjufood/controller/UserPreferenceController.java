package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.UserPreference;
import xyz.tjucomments.tjufood.service.UserPreferenceService;

import java.util.List;

@RestController
@RequestMapping("/api/preference")
public class UserPreferenceController {

    @Resource
    UserPreferenceService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<UserPreference>> byUser(@PathVariable long userId) {
        return RestBean.success(service.findByUser(userId));
    }
}

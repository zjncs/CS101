package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Activity;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.ActivityService;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    @Resource
    ActivityService service;

    @GetMapping("/ongoing")
    public RestBean<List<Activity>> listOngoing() {
        return RestBean.success(service.listOngoing());
    }

    @GetMapping("/{id}")
    public RestBean<Activity> getById(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

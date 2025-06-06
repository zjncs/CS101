package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Log;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.LogService;

import java.util.List;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Resource
    LogService service;

    @GetMapping("/latest")
    public RestBean<List<Log>> latestLogs() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<Log> getLog(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
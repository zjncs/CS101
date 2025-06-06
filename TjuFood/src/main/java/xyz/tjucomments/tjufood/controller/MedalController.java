package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Medal;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.MedalService;

import java.util.List;

@RestController
@RequestMapping("/api/medal")
public class MedalController {

    @Resource
    MedalService service;

    @GetMapping("/list")
    public RestBean<List<Medal>> listAll() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<Medal> getById(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Version;
import xyz.tjucomments.tjufood.service.VersionService;

import java.util.List;

@RestController
@RequestMapping("/api/version")
public class VersionController {

    @Resource
    VersionService service;

    @GetMapping("/list")
    public RestBean<List<Version>> list() {
        return RestBean.success(service.listPublished());
    }

    @GetMapping("/{id}")
    public RestBean<Version> get(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

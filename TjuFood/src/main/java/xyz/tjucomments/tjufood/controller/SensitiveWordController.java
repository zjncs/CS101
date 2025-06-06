package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.SensitiveWord;
import xyz.tjucomments.tjufood.service.SensitiveWordService;

import java.util.List;

@RestController
@RequestMapping("/api/sensitive-word")
public class SensitiveWordController {

    @Resource
    SensitiveWordService service;

    @GetMapping("/list")
    public RestBean<List<SensitiveWord>> list() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<SensitiveWord> get(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
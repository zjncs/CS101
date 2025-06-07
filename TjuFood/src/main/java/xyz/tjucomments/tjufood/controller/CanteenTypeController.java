package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.CanteenType;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.CanteenTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/canteen-type")
public class CanteenTypeController {

    @Resource
    CanteenTypeService service;

    @GetMapping("/list")
    public RestBean<List<CanteenType>> list() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<CanteenType> get(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
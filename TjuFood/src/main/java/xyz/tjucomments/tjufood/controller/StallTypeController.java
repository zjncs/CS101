package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.StallType;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.StallTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/stall-type")
public class StallTypeController {

    @Resource
    StallTypeService service;

    @GetMapping("/list")
    public RestBean<List<StallType>> list() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<StallType> get(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

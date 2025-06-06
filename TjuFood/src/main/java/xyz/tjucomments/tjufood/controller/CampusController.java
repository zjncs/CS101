package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Campus;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.CampusService;

import java.util.List;

@RestController
@RequestMapping("/api/campus")
public class CampusController {

    @Resource
    CampusService service;

    @GetMapping("/list")
    public RestBean<List<Campus>> listCampuses() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<Campus> getCampus(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
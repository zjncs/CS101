package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.CanteenService;

import java.util.List;

@RestController
@RequestMapping("/api/canteen")
public class CanteenController {

    @Resource
    CanteenService service;

    @GetMapping("/campus/{campusId}")
    public RestBean<List<Canteen>> listByCampus(@PathVariable long campusId) {
        return RestBean.success(service.listByCampus(campusId));
    }

    @GetMapping("/{id}")
    public RestBean<Canteen> getCanteen(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
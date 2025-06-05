package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.service.StallService;

import java.util.List;

@RestController
@RequestMapping("/api/stall")
public class StallController {

    @Resource
    StallService service;

    @GetMapping("/canteen/{canteenId}")
    public RestBean<List<Stall>> listByCanteen(@PathVariable long canteenId) {
        return RestBean.success(service.listByCanteen(canteenId));
    }

    @GetMapping("/{id}")
    public RestBean<Stall> getStall(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

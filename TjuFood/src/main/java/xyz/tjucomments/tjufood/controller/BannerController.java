package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Banner;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.BannerService;

import java.util.List;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

    @Resource
    BannerService service;

    @GetMapping("/active")
    public RestBean<List<Banner>> activeBanners() {
        return RestBean.success(service.active());
    }

    @GetMapping("/{id}")
    public RestBean<Banner> getBanner(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

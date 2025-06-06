package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Image;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Resource
    ImageService service;

    @GetMapping("/latest")
    public RestBean<List<Image>> latestImages() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<Image> getImage(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.ImageCategory;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.ImageCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/image-category")
public class ImageCategoryController {

    @Resource
    ImageCategoryService service;

    @GetMapping("/list")
    public RestBean<List<ImageCategory>> list() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<ImageCategory> get(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

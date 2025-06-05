package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Blog;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Resource
    BlogService service;

    @GetMapping("/latest")
    public RestBean<List<Blog>> latestBlogs() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<Blog> getBlog(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

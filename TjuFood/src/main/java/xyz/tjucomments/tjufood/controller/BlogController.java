package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import xyz.tjucomments.tjufood.entity.Blog;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.BlogService;

import java.util.List;

@Validated
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

    @PostMapping("/add")
    public RestBean<String> createBlog(@RequestBody Blog blog) {
        blog.setLiked(0);
        blog.setComments(0);
        blog.setStatus(0);
        blog.setIsTop(0);
        if(service.createBlog(blog))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }
}

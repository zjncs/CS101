package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Comment;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Resource
    CommentService service;

    @GetMapping("/blog/{blogId}")
    public RestBean<List<Comment>> listBlogComments(@PathVariable long blogId) {
        return RestBean.success(service.listByBlog(blogId));
    }

    @GetMapping("/review/{reviewId}")
    public RestBean<List<Comment>> listReviewComments(@PathVariable long reviewId) {
        return RestBean.success(service.listByReview(reviewId));
    }
}

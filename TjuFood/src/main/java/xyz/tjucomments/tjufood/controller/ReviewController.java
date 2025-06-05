package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Review;
import xyz.tjucomments.tjufood.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Resource
    ReviewService service;

    @GetMapping("/dish/{dishId}")
    public RestBean<List<Review>> listByDish(@PathVariable long dishId) {
        return RestBean.success(service.listByDish(dishId));
    }

    @GetMapping("/{id}")
    public RestBean<Review> getReview(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Review;
import xyz.tjucomments.tjufood.service.ReviewService;

import java.util.List;

@Validated
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

    @PostMapping("/add")
    public RestBean<String> createReview(@RequestBody Review review) {
        review.setLiked(0);
        review.setStatus(0);
        if(service.createReview(review))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }
}
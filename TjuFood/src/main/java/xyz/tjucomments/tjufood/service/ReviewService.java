package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> listByDish(long dishId);
    Review getById(long id);
    boolean createReview(Review review);
}
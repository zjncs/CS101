package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Review;
import xyz.tjucomments.tjufood.mapper.ReviewMapper;
import xyz.tjucomments.tjufood.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Resource
    ReviewMapper mapper;

    @Override
    public List<Review> listByDish(long dishId) {
        return mapper.findByDish(dishId);
    }

    @Override
    public Review getById(long id) {
        return mapper.findById(id);
    }

    @Override
    public boolean createReview(Review review) {
        return mapper.insert(review) > 0;
    }
}
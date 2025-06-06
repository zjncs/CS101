package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Comment;
import xyz.tjucomments.tjufood.mapper.CommentMapper;
import xyz.tjucomments.tjufood.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentMapper mapper;

    @Override
    public List<Comment> listByBlog(long blogId) {
        return mapper.findByBlog(blogId);
    }

    @Override
    public List<Comment> listByReview(long reviewId) {
        return mapper.findByReview(reviewId);
    }
}
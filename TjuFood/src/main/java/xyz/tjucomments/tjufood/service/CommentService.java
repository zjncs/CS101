package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listByBlog(long blogId);
    List<Comment> listByReview(long reviewId);
}
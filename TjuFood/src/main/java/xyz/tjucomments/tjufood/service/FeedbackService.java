package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> latest();
    Feedback getById(long id);
}
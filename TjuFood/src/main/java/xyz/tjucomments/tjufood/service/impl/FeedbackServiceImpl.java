package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Feedback;
import xyz.tjucomments.tjufood.mapper.FeedbackMapper;
import xyz.tjucomments.tjufood.service.FeedbackService;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    FeedbackMapper mapper;

    @Override
    public List<Feedback> latest() {
        return mapper.latestFeedback();
    }

    @Override
    public Feedback getById(long id) {
        return mapper.findById(id);
    }
}

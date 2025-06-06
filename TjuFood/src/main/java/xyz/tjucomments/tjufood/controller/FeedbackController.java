package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Feedback;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Resource
    FeedbackService service;

    @GetMapping("/latest")
    public RestBean<List<Feedback>> latestFeedback() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<Feedback> getFeedback(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

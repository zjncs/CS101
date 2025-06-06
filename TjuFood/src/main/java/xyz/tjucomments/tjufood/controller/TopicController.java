package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Topic;
import xyz.tjucomments.tjufood.service.TopicService;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Resource
    TopicService service;

    @GetMapping("/list")
    public RestBean<List<Topic>> listTopics() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{id}")
    public RestBean<Topic> getTopic(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}

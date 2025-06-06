package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Message;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Resource
    MessageService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Message>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}
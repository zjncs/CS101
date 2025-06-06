package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Sign;
import xyz.tjucomments.tjufood.service.SignService;

import java.util.List;

@RestController
@RequestMapping("/api/sign")
public class SignController {

    @Resource
    SignService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Sign>> listUserSign(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}

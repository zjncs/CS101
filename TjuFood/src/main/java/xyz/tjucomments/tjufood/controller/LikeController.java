package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.entity.AccountUser;
import xyz.tjucomments.tjufood.entity.Like;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.LikeService;

import java.util.List;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Resource
    LikeService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Like>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }

    @GetMapping("/target/{type}/{id}")
    public RestBean<List<Like>> listByTarget(@PathVariable int type, @PathVariable("id") long likedId) {
        return RestBean.success(service.listByTarget(likedId, type));
    }

    @PostMapping("/add")
    public RestBean<String> addLike(@RequestBody Like like) {
        if(service.addLike(like))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }

    @DeleteMapping("/remove/{type}/{id}")
    public RestBean<String> removeLike(@SessionAttribute("account") AccountUser user,
                                       @PathVariable int type,
                                       @PathVariable("id") long likedId) {
        if(service.removeLike(user.getId(), likedId, type))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }
}
package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.entity.AccountUser;
import xyz.tjucomments.tjufood.entity.Follow;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    @Resource
    FollowService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Follow>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }

    @PostMapping("/add")
    public RestBean<String> addFollow(@RequestBody Follow follow) {
        if(service.addFollow(follow))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }

    @DeleteMapping("/remove/{followUserId}")
    public RestBean<String> removeFollow(@SessionAttribute("account") AccountUser user,
                                         @PathVariable long followUserId) {
        if(service.removeFollow(user.getId(), followUserId))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }
}
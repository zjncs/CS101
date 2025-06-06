package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.entity.AccountUser;
import xyz.tjucomments.tjufood.entity.Favorite;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Resource
    FavoriteService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<Favorite>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }

    @PostMapping("/add")
    public RestBean<String> addFavorite(@RequestBody Favorite favorite) {
        if(service.addFavorite(favorite))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }

    @DeleteMapping("/remove/{type}/{id}")
    public RestBean<String> removeFavorite(@SessionAttribute("account") AccountUser user,
                                           @PathVariable int type,
                                           @PathVariable("id") long targetId) {
        if(service.removeFavorite(user.getId(), targetId, type))
            return RestBean.success("ok");
        return RestBean.failure(500, "fail");
    }
}

package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Ranking;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.RankingService;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    @Resource
    RankingService service;

    @GetMapping("/type/{type}")
    public RestBean<List<Ranking>> top(@PathVariable int type) {
        return RestBean.success(service.topRanking(type));
    }
}
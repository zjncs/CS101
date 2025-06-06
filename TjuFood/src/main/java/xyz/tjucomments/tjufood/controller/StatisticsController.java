package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Statistics;
import xyz.tjucomments.tjufood.service.StatisticsService;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Resource
    StatisticsService service;

    @GetMapping("/type/{type}/period/{period}")
    public RestBean<List<Statistics>> list(@PathVariable int type, @PathVariable String period) {
        return RestBean.success(service.listStatistics(type, period));
    }
}

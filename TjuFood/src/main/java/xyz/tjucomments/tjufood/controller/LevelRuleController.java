package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.LevelRule;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.LevelRuleService;

import java.util.List;

@RestController
@RequestMapping("/api/level")
public class LevelRuleController {

    @Resource
    LevelRuleService service;

    @GetMapping("/rules")
    public RestBean<List<LevelRule>> listAll() {
        return RestBean.success(service.listAll());
    }
}
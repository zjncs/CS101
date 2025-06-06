package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Config;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.ConfigService;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Resource
    ConfigService service;

    @GetMapping("/all")
    public RestBean<List<Config>> listAll() {
        return RestBean.success(service.listAll());
    }

    @GetMapping("/{key}")
    public RestBean<Config> getConfig(@PathVariable String key) {
        return RestBean.success(service.getByKey(key));
    }
}

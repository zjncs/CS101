package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.CreditRecord;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.CreditRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
public class CreditRecordController {

    @Resource
    CreditRecordService service;

    @GetMapping("/user/{userId}")
    public RestBean<List<CreditRecord>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}

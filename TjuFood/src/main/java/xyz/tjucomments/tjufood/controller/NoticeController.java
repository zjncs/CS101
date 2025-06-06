package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Notice;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Resource
    NoticeService service;

    @GetMapping("/latest")
    public RestBean<List<Notice>> latestNotices() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<Notice> getNotice(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
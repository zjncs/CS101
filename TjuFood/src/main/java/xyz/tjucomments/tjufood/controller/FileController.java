package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.File;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Resource
    FileService service;

    @GetMapping("/latest")
    public RestBean<List<File>> latestFiles() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<File> getFile(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.Report;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.service.ReportService;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Resource
    ReportService service;

    @GetMapping("/latest")
    public RestBean<List<Report>> latestReports() {
        return RestBean.success(service.latest());
    }

    @GetMapping("/{id}")
    public RestBean<Report> getReport(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
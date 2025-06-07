package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Report;
import xyz.tjucomments.tjufood.mapper.ReportMapper;
import xyz.tjucomments.tjufood.service.ReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    ReportMapper mapper;

    @Override
    public List<Report> latest() {
        return mapper.latestReport();
    }

    @Override
    public Report getById(long id) {
        return mapper.findById(id);
    }
}
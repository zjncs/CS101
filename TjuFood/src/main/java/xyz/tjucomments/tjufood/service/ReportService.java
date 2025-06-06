package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Report;

import java.util.List;

public interface ReportService {
    List<Report> latest();
    Report getById(long id);
}

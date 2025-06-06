package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Notice;

import java.util.List;

public interface NoticeService {
    List<Notice> latest();
    Notice getById(long id);
}

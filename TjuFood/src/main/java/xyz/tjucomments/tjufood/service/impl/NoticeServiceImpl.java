package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Notice;
import xyz.tjucomments.tjufood.mapper.NoticeMapper;
import xyz.tjucomments.tjufood.service.NoticeService;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    NoticeMapper mapper;

    @Override
    public List<Notice> latest() {
        return mapper.latestNotices();
    }

    @Override
    public Notice getById(long id) {
        return mapper.findById(id);
    }
}
package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Blog;
import xyz.tjucomments.tjufood.mapper.BlogMapper;
import xyz.tjucomments.tjufood.service.BlogService;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    BlogMapper mapper;

    @Override
    public List<Blog> latest() {
        return mapper.latestBlogs();
    }

    @Override
    public Blog getById(long id) {
        return mapper.findById(id);
    }
}
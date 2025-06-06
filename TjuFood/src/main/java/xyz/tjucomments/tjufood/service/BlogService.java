package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Blog;

import java.util.List;

public interface BlogService {
    List<Blog> latest();
    Blog getById(long id);
    boolean createBlog(Blog blog);
}
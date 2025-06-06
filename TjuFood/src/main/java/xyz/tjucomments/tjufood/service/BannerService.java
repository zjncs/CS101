package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> active();
    Banner getById(long id);
}

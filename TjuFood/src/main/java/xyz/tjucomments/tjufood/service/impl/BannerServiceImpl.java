package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Banner;
import xyz.tjucomments.tjufood.mapper.BannerMapper;
import xyz.tjucomments.tjufood.service.BannerService;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    BannerMapper mapper;

    @Override
    public List<Banner> active() {
        return mapper.activeBanners();
    }

    @Override
    public Banner getById(long id) {
        return mapper.findById(id);
    }
}
package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.mapper.CanteenMapper;
import xyz.tjucomments.tjufood.service.CanteenService;

import java.util.List;

@Service
public class CanteenServiceImpl implements CanteenService {

    @Resource
    CanteenMapper mapper;

    @Override
    public List<Canteen> listByCampus(long campusId) {
        return mapper.findByCampus(campusId);
    }

    @Override
    public Canteen getById(long id) {
        return mapper.findById(id);
    }
}

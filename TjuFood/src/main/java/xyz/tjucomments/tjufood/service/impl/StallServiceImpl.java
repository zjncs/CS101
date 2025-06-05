package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.mapper.StallMapper;
import xyz.tjucomments.tjufood.service.StallService;

import java.util.List;

@Service
public class StallServiceImpl implements StallService {

    @Resource
    StallMapper mapper;

    @Override
    public List<Stall> listByCanteen(long canteenId) {
        return mapper.findByCanteen(canteenId);
    }

    @Override
    public Stall getById(long id) {
        return mapper.findById(id);
    }
}

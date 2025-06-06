package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Sign;
import xyz.tjucomments.tjufood.mapper.SignMapper;
import xyz.tjucomments.tjufood.service.SignService;

import java.util.List;

@Service
public class SignServiceImpl implements SignService {

    @Resource
    SignMapper mapper;

    @Override
    public List<Sign> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}
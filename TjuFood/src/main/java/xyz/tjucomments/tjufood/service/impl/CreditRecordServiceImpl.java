package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.CreditRecord;
import xyz.tjucomments.tjufood.mapper.CreditRecordMapper;
import xyz.tjucomments.tjufood.service.CreditRecordService;

import java.util.List;

@Service
public class CreditRecordServiceImpl implements CreditRecordService {

    @Resource
    CreditRecordMapper mapper;

    @Override
    public List<CreditRecord> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}
package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Message;
import xyz.tjucomments.tjufood.mapper.MessageMapper;
import xyz.tjucomments.tjufood.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    MessageMapper mapper;

    @Override
    public List<Message> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}

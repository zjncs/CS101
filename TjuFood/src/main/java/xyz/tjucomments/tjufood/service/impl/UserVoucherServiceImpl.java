package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.UserVoucher;
import xyz.tjucomments.tjufood.mapper.UserVoucherMapper;
import xyz.tjucomments.tjufood.service.UserVoucherService;

import java.util.List;

@Service
public class UserVoucherServiceImpl implements UserVoucherService {

    @Resource
    UserVoucherMapper mapper;

    @Override
    public List<UserVoucher> listByUser(long userId) {
        return mapper.findByUser(userId);
    }
}

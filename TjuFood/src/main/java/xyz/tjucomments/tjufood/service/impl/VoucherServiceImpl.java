package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Voucher;
import xyz.tjucomments.tjufood.mapper.VoucherMapper;
import xyz.tjucomments.tjufood.service.VoucherService;

import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Resource
    VoucherMapper mapper;

    @Override
    public List<Voucher> listActive() {
        return mapper.listActive();
    }

    @Override
    public Voucher getById(long id) {
        return mapper.findById(id);
    }
}

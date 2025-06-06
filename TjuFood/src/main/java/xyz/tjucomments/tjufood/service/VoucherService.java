package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.Voucher;

import java.util.List;

public interface VoucherService {
    List<Voucher> listActive();
    Voucher getById(long id);
}
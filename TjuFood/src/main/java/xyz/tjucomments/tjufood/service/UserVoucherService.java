package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.UserVoucher;

import java.util.List;

public interface UserVoucherService {
    List<UserVoucher> listByUser(long userId);
}

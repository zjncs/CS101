package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.UserVoucher;
import xyz.tjucomments.tjufood.service.UserVoucherService;

import java.util.List;

@RestController
@RequestMapping("/api/user/voucher")
public class UserVoucherController {

    @Resource
    UserVoucherService service;

    @GetMapping("/{userId}")
    public RestBean<List<UserVoucher>> listByUser(@PathVariable long userId) {
        return RestBean.success(service.listByUser(userId));
    }
}

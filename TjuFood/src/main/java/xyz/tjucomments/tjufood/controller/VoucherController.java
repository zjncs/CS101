package xyz.tjucomments.tjufood.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tjucomments.tjufood.entity.RestBean;
import xyz.tjucomments.tjufood.entity.Voucher;
import xyz.tjucomments.tjufood.service.VoucherService;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {

    @Resource
    VoucherService service;

    @GetMapping("/active")
    public RestBean<List<Voucher>> listActive() {
        return RestBean.success(service.listActive());
    }

    @GetMapping("/{id}")
    public RestBean<Voucher> getById(@PathVariable long id) {
        return RestBean.success(service.getById(id));
    }
}
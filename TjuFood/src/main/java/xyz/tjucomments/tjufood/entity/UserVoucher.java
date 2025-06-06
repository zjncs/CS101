package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVoucher {
    private Long id;
    private Long userId;
    private Long voucherId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime useTime;
}
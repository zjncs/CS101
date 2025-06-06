package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Follow {
    private Long id;
    private Long userId;
    private Long followUserId;
    private LocalDateTime createTime;
}
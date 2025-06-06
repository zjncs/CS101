package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {
    private Long id;
    private Long userId;
    private Long likedId;
    private Integer type;
    private LocalDateTime createTime;
}

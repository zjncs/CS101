package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private Long fromUserId;
    private Long toUserId;
    private Integer type;
    private Long sourceId;
    private Integer sourceType;
    private String content;
    private Integer readStatus;
    private LocalDateTime createTime;
}

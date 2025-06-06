package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notice {
    private Long id;
    private Long adminId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Blog {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String images;
    private Long canteenId;
    private Long stallId;
    private Integer liked;
    private Integer comments;
    private Integer status;
    private Integer isTop;
    private LocalDateTime createTime;
}
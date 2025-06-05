package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long userId;
    private Long blogId;
    private Long reviewId;
    private Long parentId;
    private String content;
    private Integer liked;
    private Integer status;
    private LocalDateTime createTime;
}

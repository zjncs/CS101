package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Feedback {
    private Long id;
    private Long userId;
    private Integer type;
    private String content;
    private String contact;
    private Integer status;
    private String response;
    private Long adminId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

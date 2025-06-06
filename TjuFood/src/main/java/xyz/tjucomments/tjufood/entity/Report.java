package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Report {
    private Long id;
    private Long userId;
    private Long targetId;
    private Integer type;
    private String reason;
    private String description;
    private String images;
    private Integer status;
    private Long handleAdminId;
    private String handleNote;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

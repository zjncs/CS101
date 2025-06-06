package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Banner {
    private Long id;
    private String title;
    private String image;
    private String link;
    private Long targetId;
    private Integer targetType;
    private Integer sort;
    private Integer status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Activity {
    private Long id;
    private String title;
    private String description;
    private String cover;
    private Integer type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String rules;
    private String rewards;
    private Integer status;
    private Long adminId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

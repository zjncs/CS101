package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Medal {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String condition;
    private Integer type;
    private Integer credits;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

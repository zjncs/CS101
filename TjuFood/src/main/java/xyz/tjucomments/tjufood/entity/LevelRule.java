package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class LevelRule {
    private Long id;
    private Integer level;
    private String name;
    private Integer minCredits;
    private Integer maxCredits;
    private String benefits;
    private String icon;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

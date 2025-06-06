package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserPreference {
    private Long id;
    private Long userId;
    private Integer preferenceType;
    private String preferenceValue;
    private Double weight;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

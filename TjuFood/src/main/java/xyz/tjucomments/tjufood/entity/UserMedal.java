package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserMedal {
    private Long id;
    private Long userId;
    private Long medalId;
    private LocalDateTime obtainTime;
    private Integer isDisplay;
}
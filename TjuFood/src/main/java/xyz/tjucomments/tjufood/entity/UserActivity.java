package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserActivity {
    private Long id;
    private Long userId;
    private Long activityId;
    private LocalDateTime joinTime;
    private Integer status;
    private Integer progress;
    private Integer rewardStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
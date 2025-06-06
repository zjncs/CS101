package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Ranking {
    private Long id;
    private Integer type;
    private Long targetId;
    private Double score;
    private Integer rank;
    private LocalDateTime updateTime;
}
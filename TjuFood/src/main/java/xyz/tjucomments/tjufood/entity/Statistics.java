package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Statistics {
    private Long id;
    private Integer type;
    private Long targetId;
    private String period;
    private LocalDate date;
    private String data;
    private LocalDateTime createTime;
}

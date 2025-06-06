package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Sign {
    private Long id;
    private Long userId;
    private Integer year;
    private Integer month;
    private LocalDate date;
    private LocalDateTime createTime;
}

package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Config {
    private Long id;
    private String key;
    private String value;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Image {
    private Long id;
    private Long categoryId;
    private Long adminId;
    private String name;
    private String url;
    private Integer width;
    private Integer height;
    private Integer size;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
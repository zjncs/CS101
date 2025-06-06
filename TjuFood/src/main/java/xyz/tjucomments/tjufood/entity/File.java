package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class File {
    private Long id;
    private Long userId;
    private String name;
    private String originalName;
    private String url;
    private String type;
    private Integer size;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Integer status;
    private LocalDateTime createTime;
}

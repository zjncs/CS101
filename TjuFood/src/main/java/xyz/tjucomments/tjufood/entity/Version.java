package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Version {
    private Long id;
    private String version;
    private Integer platform;
    private String description;
    private String downloadUrl;
    private Integer isForce;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Log {
    private Long id;
    private Long userId;
    private Long adminId;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private String userAgent;
    private Integer operationTime;
    private LocalDateTime createTime;
}

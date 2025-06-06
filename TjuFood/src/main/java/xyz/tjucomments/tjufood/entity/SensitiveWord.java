package xyz.tjucomments.tjufood.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SensitiveWord {
    private Long id;
    private String word;
    private Integer type;
    private String replaceWord;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

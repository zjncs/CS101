package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Long id;
    private Long userId;
    private String content;
    private String images;
    private Long canteenId;
    private Long stallId;
    private Long dishId;
    private Integer overallScore;
    private Integer tasteScore;
    private Integer environmentScore;
    private Integer serviceScore;
    private Integer priceScore;
    private Integer liked;
    private Integer status;
    private LocalDateTime createTime;
}

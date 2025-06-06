package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class Stall {
    private Long id;
    private String name;
    private Long canteenId;
    private Long typeId;
    private String images;
    private String location;
    private String introduction;
    private String openHours;
    private Double score;
    private Double tasteScore;
    private Double priceScore;
    private Integer comments;
    private Integer openStatus;
}

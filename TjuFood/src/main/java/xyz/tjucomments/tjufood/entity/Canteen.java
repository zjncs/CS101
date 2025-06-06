package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class Canteen {
    private Long id;
    private String name;
    private Long campusId;
    private Long typeId;
    private String images;
    private String address;
    private Double x;
    private Double y;
    private String floor;
    private String openHours;
    private Integer avgPrice;
    private String introduction;
    private Double score;
    private Double tasteScore;
    private Double environmentScore;
    private Double serviceScore;
    private Integer liked;
    private Integer comments;
    private Integer openStatus;
}
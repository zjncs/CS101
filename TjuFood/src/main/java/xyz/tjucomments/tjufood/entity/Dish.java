package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class Dish {
    private Long id;
    private String name;
    private Long stallId;
    private String category;
    private Double price;
    private String description;
    private String images;
    private String nutrition;
    private Integer isSpecial;
    private Integer isLimited;
    private Double score;
    private Integer comments;
    private Integer status;
}
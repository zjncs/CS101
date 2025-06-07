package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class StallType {
    private Long id;
    private String name;
    private String icon;
    private Integer sort;
}
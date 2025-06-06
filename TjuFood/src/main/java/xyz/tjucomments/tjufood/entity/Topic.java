package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class Topic {
    private Long id;
    private String name;
    private String description;
    private String cover;
    private Integer sort;
    private Integer status;
}
package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class Campus {
    private Long id;
    private String name;
    private String address;
    private Double x;
    private Double y;
    private Integer sort;
}

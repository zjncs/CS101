package xyz.tjucomments.tjufood.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.tjucomments.tjufood.entity.Dish;

@Data
@EqualsAndHashCode(callSuper = true)
public class DishDTO extends Dish {
    private String stallName;   // 关联的窗口名称
    private String canteenName; // 关联的食堂名称
}
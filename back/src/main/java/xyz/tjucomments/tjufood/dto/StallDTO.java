package xyz.tjucomments.tjufood.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.tjucomments.tjufood.entity.Stall;

@Data
@EqualsAndHashCode(callSuper = true)
public class StallDTO extends Stall {
    private String canteenName; // 关联的食堂名称
}
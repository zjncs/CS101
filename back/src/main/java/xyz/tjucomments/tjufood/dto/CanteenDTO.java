package xyz.tjucomments.tjufood.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.tjucomments.tjufood.entity.Canteen;

@Data
@EqualsAndHashCode(callSuper = true)
public class CanteenDTO extends Canteen {
    private String campusName; // 关联的校区名称
    private String typeName;   // 关联的类型名称
}

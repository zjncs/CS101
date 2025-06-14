package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "菜品实体对象")
@Data
@TableName("tb_dish")
public class Dish {

    @Schema(description = "菜品唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "菜品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "扬州炒饭")
    @TableField("name")
    private String name;

    @Schema(description = "所属窗口ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @TableField("stall_id")
    private Long stallId;

    @Schema(description = "菜品分类", example = "主食")
    @TableField("category")
    private String category;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "12.50")
    @TableField("price")
    private BigDecimal price;

    @Schema(description = "菜品描述", example = "经典炒饭，配料丰富")
    @TableField("description")
    private String description;

    @Schema(description = "评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("score")
    private Double score;

    @Schema(description = "评论提及次数", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("comments")
    private Integer comments;

    @Schema(description = "状态 (0=下架, 1=上架)", example = "1")
    @TableField("status")
    private Integer status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("update_time")
    private LocalDateTime updateTime;
}

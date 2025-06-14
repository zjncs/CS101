package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "窗口实体对象")
@Data
@TableName("tb_stall")
public class Stall {

    @Schema(description = "窗口唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "窗口名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "特色炒饭窗口")
    @TableField("name")
    private String name;

    @Schema(description = "所属食堂ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @TableField("canteen_id")
    private Long canteenId;

    @Schema(description = "窗口类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @TableField("type_id")
    private Long typeId;

    @Schema(description = "窗口位置编号", example = "二楼E05")
    @TableField("location")
    private String location;

    @Schema(description = "窗口介绍", example = "提供各种口味的炒饭")
    @TableField("introduction")
    private String introduction;

    @Schema(description = "营业时间", example = "10:00-20:00")
    @TableField("open_hours")
    private String openHours;

    @Schema(description = "综合评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("score")
    private Double score;

    @Schema(description = "口味评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("taste_score")
    private Double tasteScore;

    @Schema(description = "性价比评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("price_score")
    private Double priceScore;

    @Schema(description = "评论数", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("comments")
    private Integer comments;

    @Schema(description = "营业状态 (0=休息, 1=营业)", example = "1")
    @TableField("open_status")
    private Integer openStatus;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("update_time")
    private LocalDateTime updateTime;
}

package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "食堂实体对象")
@Data
@TableName("tb_canteen")
public class Canteen {

    @Schema(description = "食堂唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "食堂名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "学三食堂")
    @TableField("name")
    private String name;

    @Schema(description = "所属校区ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @TableField("campus_id")
    private Long campusId;

    @Schema(description = "食堂类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @TableField("type_id")
    private Long typeId;

    @Schema(description = "详细地址", example = "青年湖西侧")
    @TableField("address")
    private String address;

    @Schema(description = "楼层信息", example = "共三层")
    @TableField("floor")
    private String floor;

    @Schema(description = "营业时间", example = "07:00-21:00")
    @TableField("open_hours")
    private String openHours;

    @Schema(description = "人均价格", example = "25")
    @TableField("avg_price")
    private Integer avgPrice;

    @Schema(description = "食堂介绍", example = "物美价廉，种类繁多")
    @TableField("introduction")
    private String introduction;

    @Schema(description = "综合评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("score")
    private Double score;

    @Schema(description = "口味评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("taste_score")
    private Double tasteScore;

    @Schema(description = "环境评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("environment_score")
    private Double environmentScore;

    @Schema(description = "服务评分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("service_score")
    private Double serviceScore;

    @Schema(description = "点赞数", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("liked")
    private Integer liked;

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

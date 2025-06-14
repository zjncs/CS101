package xyz.tjucomments.tjufood.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;

@Schema(description = "用户实体对象")
@Data
@TableName("tb_user")
public class User {

    @Schema(description = "用户唯一ID", accessMode = Schema.AccessMode.READ_ONLY)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "password123", accessMode = Schema.AccessMode.WRITE_ONLY)
    @TableField("password")
    private String password;

    @Schema(description = "用户邮箱 (用于登录和验证)", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com")
    @TableField("email")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    @TableField("phone")
    private String phone;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "爱吃饭的同学")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "头像URL", example = "http://example.com/icon.png")
    @TableField("icon")
    private String icon;

    @Schema(description = "性别 (0=女, 1=男, 2=未知)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @TableField("gender")
    private Integer gender;

    @Schema(description = "生日", example = "2000-01-01")
    @TableField("birthday")
    private Date birthday;

    @Schema(description = "个人简介", example = "大家好，我爱美食！")
    @TableField("bio")
    private String bio;

    @Schema(description = "所属校区", requiredMode = Schema.RequiredMode.REQUIRED, example = "卫津路校区")
    @TableField("campus")
    private String campus;

    @Schema(description = "积分", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("credits")
    private Integer credits;

    @Schema(description = "用户等级", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("level")
    private Integer level;

    @Schema(description = "状态 (0=正常, 1=禁用)", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("status")
    private Integer status;

    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    @TableField("update_time")
    private LocalDateTime updateTime;
}

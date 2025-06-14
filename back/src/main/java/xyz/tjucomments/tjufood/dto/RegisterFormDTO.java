// --- 文件: dto/RegisterFormDTO.java ---
package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class RegisterFormDTO {
    // 核心注册信息
    private String email;
    private String password;
    private String confirmPassword;
    private String code; // 邮箱验证码

    // 用户基本信息
    private String nickname;
    private Integer gender;
    private String campus;

}


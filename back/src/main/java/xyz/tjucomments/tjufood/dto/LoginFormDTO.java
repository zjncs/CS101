package xyz.tjucomments.tjufood.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginFormDTO {

    /**
     * 【重要修正】登录账号字段。
     * 此字段用于接收用户输入的邮箱或ID，以匹配 `UserServiceImpl` 中的登录逻辑。
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}
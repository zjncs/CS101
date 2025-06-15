package xyz.tjucomments.tjufood.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterFormDTO {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    // 【重要修正】添加了注册时需要提交的验证码字段
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码格式错误") // 假设验证码是6位数字
    private String code;

    // 以下字段保持您原有的设计
    private String nickname;
    private Integer gender;
    private String campus;
}
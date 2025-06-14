// --- 文件: dto/SendCodeDTO.java (新增) ---
package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class SendCodeDTO {
    private String email;
    private Integer type; // 0=注册, 2=忘记密码
    private String captchaKey; // 图形验证码的唯一标识
    private String captchaCode; // 用户输入的图形验证码
}
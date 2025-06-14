package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String account; // 可以是邮箱或用户ID
    private String password;
}


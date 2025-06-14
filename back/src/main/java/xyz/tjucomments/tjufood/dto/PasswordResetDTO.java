package xyz.tjucomments.tjufood.dto;

import lombok.Data;

@Data
public class PasswordResetDTO {
    private String email;
    private String code;
    private String newPassword;
}

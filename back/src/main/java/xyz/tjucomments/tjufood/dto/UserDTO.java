package xyz.tjucomments.tjufood.dto;

import lombok.Data;

/**
 * 用户信息的数据传输对象（DTO）
 * 用于在系统的不同层之间传递简化的、安全的用户信息，避免暴露完整的实体类。
 */
@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String icon;
}

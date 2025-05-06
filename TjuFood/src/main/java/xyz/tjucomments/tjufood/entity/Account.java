package xyz.tjucomments.tjufood.entity;

import lombok.Data;

@Data
public class Account {
    int id;
    String email;
    String username;
    String password;
    String phone;
    String nickname;

}

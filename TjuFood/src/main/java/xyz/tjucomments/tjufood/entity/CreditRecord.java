package xyz.tjucomments.tjufood.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CreditRecord {
    long id;
    long userId;
    String type;
    int credits;
    String description;
    Date createTime;
}

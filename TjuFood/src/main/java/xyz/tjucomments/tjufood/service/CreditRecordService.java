package xyz.tjucomments.tjufood.service;

import xyz.tjucomments.tjufood.entity.CreditRecord;

import java.util.List;

public interface CreditRecordService {
    List<CreditRecord> listByUser(long userId);
}
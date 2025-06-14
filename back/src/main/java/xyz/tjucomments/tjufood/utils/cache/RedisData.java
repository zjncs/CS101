package xyz.tjucomments.tjufood.utils.cache;

import java.time.LocalDateTime;

/**
 * 用于逻辑过期缓存的数据包装类
 * 使用 Java 17 的 record 关键字，代码更简洁且天生不可变
 *
 * @param expireTime 逻辑过期时间
 * @param data       实际数据
 */
public record RedisData(LocalDateTime expireTime, Object data) {
}

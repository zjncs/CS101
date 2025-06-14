package xyz.tjucomments.tjufood;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RedisConnectionTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testRedisConnection() {
        // 1. 定义一个key和value
        String key = "test:connection";
        String value = "success";

        // 2. 尝试向Redis写入数据
        stringRedisTemplate.opsForValue().set(key, value);

        // 3. 尝试从Redis读取数据
        String retrievedValue = stringRedisTemplate.opsForValue().get(key);

        // 4. 断言写入和读取的值是否一致
        assertEquals(value, retrievedValue);

        System.out.println("成功连接到Redis并完成读写测试！");

        // 5. 清理测试数据
        stringRedisTemplate.delete(key);
    }
}
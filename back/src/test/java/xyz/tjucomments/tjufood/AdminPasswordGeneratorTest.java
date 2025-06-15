package xyz.tjucomments.tjufood;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 管理员密码生成器测试
 * <p>
 * 目的: 用于生成加密后的管理员密码字符串，方便手动插入数据库。
 * 使用方法:
 * 1. 修改下面 testGeneratePassword() 方法中的 "123456" 为您想要的管理员初始密码。
 * 2. 运行这个测试类。
 * 3. 复制控制台打印输出的一长串加密后的密码。
 * 4. 将复制的密码字符串粘贴到数据库 tb_admin 表的 password 字段中。
 */
@SpringBootTest // 标注这是一个 Spring Boot 测试，会加载整个 Spring 应用上下文
class AdminPasswordGeneratorTest {

    // 自动从 Spring 容器中注入我们在 SecurityConfig 里配置好的 PasswordEncoder Bean
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void testGeneratePassword() {
        // 在这里修改为您想要的管理员密码明文
        String rawPassword = "123456";

        // 使用注入的 BCryptPasswordEncoder 对明文密码进行加密
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 在控制台打印出加密后的结果
        System.out.println("================================================================");
        System.out.println("原始密码: " + rawPassword);
        System.out.println("BCrypt 加密后: " + encodedPassword);
        System.out.println("================================================================");
        System.out.println("请复制上面的加密后字符串到数据库中。");

        // 提示：BCrypt 每次加密的结果都不同，这是正常现象，因为它内置了随机盐。
        // 任何一次生成的结果都是有效的。
    }
}

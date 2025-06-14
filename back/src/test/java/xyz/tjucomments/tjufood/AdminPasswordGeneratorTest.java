package xyz.tjucomments.tjufood;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.tjucomments.tjufood.utils.security.PasswordEncoder;

/**
 * 这是一个专门用于生成管理员加密密码的工具测试类。
 * 运行此测试，即可在控制台获得一个加密后的密码字符串。
 */
@SpringBootTest
class AdminPasswordGeneratorTest {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void generateAdminPassword() {
        // 在这里定义您想要的管理员明文密码
        String plainPassword = "123456";

        // 使用我们项目中的PasswordEncoder来加密
        String encodedPassword = passwordEncoder.encode(plainPassword);

        // 在控制台打印出结果
        System.out.println("====================================================================");
        System.out.println("明文密码 '" + plainPassword + "' 加密后的结果是：");
        System.out.println(encodedPassword);
        System.out.println("请将上面这串加密后的密码复制到您的数据库INSERT语句中。");
        System.out.println("====================================================================");
    }
}

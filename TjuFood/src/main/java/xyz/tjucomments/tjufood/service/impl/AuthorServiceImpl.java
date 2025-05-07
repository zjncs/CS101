package xyz.tjucomments.tjufood.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.entity.Account;
import xyz.tjucomments.tjufood.mapper.UserMapper;
import xyz.tjucomments.tjufood.service.AuthorService;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorServiceImpl implements AuthorService{

    @Value("${spring.mail.username}")
    String from;

    @Resource
    UserMapper mapper;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    // 临时使用内存缓存
    private final ConcurrentHashMap<String, String> emailCodeCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> emailCodeExpireTime = new ConcurrentHashMap<>();

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null)
            throw new UsernameNotFoundException("用户名不能为空");
        Account account = mapper.findAccountByNameOrEmail(username);
        if(account == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }
    @Override
    public String sendValidateEmail(String email, String sessionId, boolean hasAccount) {
        // 测试Redis连接
        try {
            System.out.println("=== Redis连接测试开始 ===");
            System.out.println("尝试连接Redis服务器: 192.168.68.139:6379");
            
            // 测试连接
            template.getConnectionFactory().getConnection().ping();
            System.out.println("Redis PING 成功");
            
            // 测试写入
            String testKey = "test:connection:" + System.currentTimeMillis();
            template.opsForValue().set(testKey, "test", 1, TimeUnit.MINUTES);
            System.out.println("Redis写入测试成功");
            
            // 测试读取
            String testValue = template.opsForValue().get(testKey);
            System.out.println("Redis读取测试值: " + testValue);
            
            if (testValue == null || !testValue.equals("test")) {
                System.out.println("Redis测试值不匹配");
                return "Redis连接测试失败";
            }
            
            System.out.println("=== Redis连接测试成功 ===");
        } catch (Exception e) {
            System.out.println("=== Redis连接异常 ===");
            System.out.println("异常类型: " + e.getClass().getName());
            System.out.println("异常信息: " + e.getMessage());
            e.printStackTrace();
            return "Redis连接异常: " + e.getMessage();
        }

        String key = "email:" + sessionId + ":" + email + ":" + hasAccount;
        
        // 检查是否频繁请求
        if (emailCodeExpireTime.containsKey(key)) {
            long expireTime = emailCodeExpireTime.get(key);
            if (System.currentTimeMillis() < expireTime && expireTime - System.currentTimeMillis() > 120000) {
                return "请求频繁，请稍后再试";
            }
        }

        Account account = mapper.findAccountByNameOrEmail(email);
        if(hasAccount && account == null) return "没有此邮件地址的账户";
        if(!hasAccount && account != null) return "此邮箱已被其他用户注册";

        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("您的验证邮件");
        message.setText("验证码是："+code);
        try {
            mailSender.send(message);
            // 使用内存缓存存储验证码
            emailCodeCache.put(key, String.valueOf(code));
            emailCodeExpireTime.put(key, System.currentTimeMillis() + 180000); // 3分钟过期
            return null;
        } catch (MailException e) {
            e.printStackTrace();
            return "邮件发送失败，请检查邮件地址是否有效";
        }
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":false";
        if (emailCodeCache.containsKey(key)) {
            String savedCode = emailCodeCache.get(key);
            if (System.currentTimeMillis() > emailCodeExpireTime.get(key)) {
                emailCodeCache.remove(key);
                emailCodeExpireTime.remove(key);
                return "验证码失效，请重新请求";
            }
            if (savedCode.equals(code)) {
                Account account = mapper.findAccountByNameOrEmail(username);
                if(account != null) return "此用户名已被注册，请更换用户名";
                emailCodeCache.remove(key);
                emailCodeExpireTime.remove(key);
                password = encoder.encode(password);
                if (mapper.createAccount(username, password, email) > 0) {
                    return null;
                } else {
                    return "内部错误，请联系管理员";
                }
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "请先请求一封验证码邮件";
        }
    }

    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email + ":true";
        if (emailCodeCache.containsKey(key)) {
            String savedCode = emailCodeCache.get(key);
            if (System.currentTimeMillis() > emailCodeExpireTime.get(key)) {
                emailCodeCache.remove(key);
                emailCodeExpireTime.remove(key);
                return "验证码失效，请重新请求";
            }
            if (savedCode.equals(code)) {
                emailCodeCache.remove(key);
                emailCodeExpireTime.remove(key);
                return null;
            } else {
                return "验证码错误，请检查后再提交";
            }
        } else {
            return "请先请求一封验证码邮件";
        }
    }

    @Override
    public boolean resetPassword(String password, String email) {
        password = encoder.encode(password);
        return mapper.resetPasswordByEmail(password, email) > 0;
    }
}

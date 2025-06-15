package xyz.tjucomments.tjufood.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.*;
import xyz.tjucomments.tjufood.entity.User;
import xyz.tjucomments.tjufood.mapper.UserMapper;
import xyz.tjucomments.tjufood.service.IUserService;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;
import xyz.tjucomments.tjufood.utils.validation.RegexUtils;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private PasswordEncoder passwordEncoder; // 注入BCrypt加密器

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Result sendCode(SendCodeDTO sendCodeDTO) {
        // 图形验证码逻辑
        String captchaKey = RedisConstants.CAPTCHA_CODE_KEY + sendCodeDTO.getCaptchaKey();
        String cachedCaptcha = stringRedisTemplate.opsForValue().get(captchaKey);
        stringRedisTemplate.delete(captchaKey);
        if (cachedCaptcha == null || !cachedCaptcha.equalsIgnoreCase(sendCodeDTO.getCaptchaCode())) {
            return Result.fail("图形验证码错误！");
        }

        String email = sendCodeDTO.getEmail();
        Integer type = sendCodeDTO.getType();
        if (RegexUtils.isEmailInvalid(email)) {
            return Result.fail("邮箱格式不正确！");
        }

        User user = getOne(new QueryWrapper<User>().eq("email", email));

        if (type == 0 && user != null) return Result.fail("该邮箱已被注册！");
        if (type == 2 && user == null) return Result.fail("该邮箱未注册！");

        // 发送邮件验证码逻辑
        String code = RandomUtil.randomNumbers(6);
        String emailCodeKey = RedisConstants.LOGIN_CODE_KEY + type + ":" + email;
        stringRedisTemplate.opsForValue().set(emailCodeKey, code, RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("TjuFood 验证码");
        message.setText("您的验证码是：" + code + "，有效期5分钟，请勿泄露。");
        mailSender.send(message);

        return Result.ok("验证码发送成功");
    }

    @Override
    @Transactional
    public Result register(RegisterFormDTO registerForm) {
        // 注册校验逻辑
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            return Result.fail("两次输入的密码不一致！");
        }

        if (RegexUtils.isCodeInvalid(registerForm.getCode())) {
            return Result.fail("验证码格式错误！");
        }

        long count = count(new QueryWrapper<User>().eq("email", registerForm.getEmail()));
        if(count > 0){
            return Result.fail("该邮箱已被注册！");
        }

        String key = RedisConstants.LOGIN_CODE_KEY + "0:" + registerForm.getEmail();
        String cachedCode = stringRedisTemplate.opsForValue().get(key);
        if (cachedCode == null || !cachedCode.equals(registerForm.getCode())) {
            return Result.fail("邮箱验证码错误！");
        }

        // 创建用户
        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        user.setNickname(registerForm.getNickname());
        user.setGender(registerForm.getGender());
        user.setCampus(registerForm.getCampus());

        try {
            save(user);
        } catch (Exception e) {
            log.error("用户注册时数据库操作失败！", e);
            return Result.fail("注册失败，请稍后重试");
        }

        stringRedisTemplate.delete(key);
        return Result.ok("注册成功！");
    }

    @Override
    public Result login(LoginFormDTO loginForm) {
        User user;
        // 【注意】此处的 .getAccount() 调用现在是正确的，因为 LoginFormDTO 已包含 account 字段
        String account = loginForm.getAccount();

        // 根据账号是邮箱还是ID进行查询
        if (account.contains("@")) {
            user = getOne(new QueryWrapper<User>().eq("email", account));
        } else {
            try {
                user = getById(Long.parseLong(account));
            } catch (NumberFormatException e) {
                return Result.fail("账号格式错误！");
            }
        }

        // 使用BCrypt比对密码
        if (user == null || !passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            return Result.fail("用户名或密码错误！");
        }

        // 生成Token并存入Redis
        String token = UUID.randomUUID().toString(true);
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        try {
            String userDTOJson = objectMapper.writeValueAsString(userDTO);
            String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
            stringRedisTemplate.opsForValue().set(tokenKey, userDTOJson, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        } catch (JsonProcessingException e) {
            log.error("用户登录序列化DTO失败", e);
            return Result.fail("登录失败，系统内部错误");
        }
        return Result.ok(token);
    }

    @Override
    public Result resetPassword(PasswordResetDTO resetForm) {
        // 重置密码逻辑
        String key = RedisConstants.LOGIN_CODE_KEY + "2:" + resetForm.getEmail();
        String cachedCode = stringRedisTemplate.opsForValue().get(key);
        if (cachedCode == null || !cachedCode.equals(resetForm.getCode())) {
            return Result.fail("验证码错误！");
        }
        String newPassword = passwordEncoder.encode(resetForm.getNewPassword());

        // 这里假设您的 mapper 中有 updatePasswordByEmail 方法
        baseMapper.updatePasswordByEmail(resetForm.getEmail(), newPassword);

        stringRedisTemplate.delete(key);
        return Result.ok("密码重置成功！");
    }
}
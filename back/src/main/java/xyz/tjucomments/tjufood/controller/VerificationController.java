package xyz.tjucomments.tjufood.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.SendCodeDTO;
import xyz.tjucomments.tjufood.service.IUserService;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Tag(name = "1. 验证码服务", description = "提供图形和邮箱验证码的接口")
@RestController
@RequestMapping("/api/verifications")
public class VerificationController {

    @Resource
    private IUserService userService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Operation(summary = "获取图形验证码", description = "用于后续操作（如发送邮箱验证码）的人机校验")
    @GetMapping("/captcha")
    public Result getCaptcha() {
        // 1. 使用easy-captcha生成算术验证码 (例如： 8 + 2 = ?)
        Captcha captcha = new ArithmeticCaptcha(120, 40);
        // 获取验证码的计算结果
        String text = captcha.text();
        // 获取验证码的唯一标识Key
        String key = UUID.randomUUID().toString();

        // 2. 将验证码文本存入Redis，有效期2分钟
        stringRedisTemplate.opsForValue().set(
                RedisConstants.CAPTCHA_CODE_KEY + key, text,
                RedisConstants.CAPTCHA_CODE_TTL, TimeUnit.MINUTES);

        // 3. 将图片转为Base64编码
        String base64Img = captcha.toBase64();

        // 4. 返回Key和Base64图片
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        map.put("image", base64Img); // base64Img已经包含了 "data:image/png;base64," 前缀
        return Result.ok(map);
    }

    @Operation(summary = "发送邮箱验证码", description = "校验图形验证码后，向指定邮箱发送验证码（用于注册或重置密码）")
    @PostMapping("/code")
    public Result sendCode(@RequestBody SendCodeDTO sendCodeDTO) {
        return userService.sendCode(sendCodeDTO);
    }
}

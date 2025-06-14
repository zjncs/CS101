package xyz.tjucomments.tjufood.utils.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.tjucomments.tjufood.dto.UserDTO;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;
@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 构造函数注入是最佳实践
    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头获取token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            return true;
        }

        // 2. 基于TOKEN从Redis获取用户
        String key = RedisConstants.LOGIN_USER_KEY + token;
        String userJson = stringRedisTemplate.opsForValue().get(key);

        // 3. 判断用户是否存在
        if (StrUtil.isBlank(userJson)) {
            return true;
        }

        // 4. 将JSON反序列化为UserDTO
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);

        // 5. 保存用户信息到 ThreadLocal
        UserHolder.saveUser(userDTO);

        // 6. 刷新token有效期
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.SECONDS);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
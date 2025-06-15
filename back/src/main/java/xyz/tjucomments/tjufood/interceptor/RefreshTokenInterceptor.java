package xyz.tjucomments.tjufood.interceptor;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.tjucomments.tjufood.dto.UserDTO;
import xyz.tjucomments.tjufood.interceptor.UserHolder;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

import java.util.concurrent.TimeUnit;

/**
 * Token刷新拦截器
 * 作用: 拦截所有请求，检查请求头中的Token，如果Token有效，则刷新其在Redis中的有效期，
 * 并将用户信息存入ThreadLocal，方便后续流程直接获取。
 * 注意: 这个拦截器不执行真正的拦截操作，无论用户是否存在都会放行。
 */
@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    // Redis操作模板
    private final StringRedisTemplate stringRedisTemplate;
    // Jackson的JSON处理工具，线程安全，可作为静态成员
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 构造函数注入StringRedisTemplate
     * @param stringRedisTemplate Spring容器注入的Redis操作实例
     */
    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 在Controller方法执行前调用
     * @return 返回true表示放行，继续执行后续的拦截器或Controller
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头中获取名为 "authorization" 的Token
        String token = request.getHeader("authorization");

        // 2. 如果Token为空，说明是未登录用户或游客，直接放行
        if (StrUtil.isBlank(token)) {
            return true;
        }

        // 3. 根据Token构建在Redis中存储的键
        String key = RedisConstants.LOGIN_USER_KEY + token;
        // 4. 从Redis中获取存储的用户信息（JSON字符串格式）
        String userJson = stringRedisTemplate.opsForValue().get(key);

        // 5. 如果Redis中没有找到对应的用户信息，说明Token无效或已过期，直接放行
        if (StrUtil.isBlank(userJson)) {
            return true;
        }

        // 6. 将查到的JSON字符串反序列化为UserDTO对象
        UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);

        // 7. 将用户信息存入UserHolder（基于ThreadLocal），以便当前线程的任何地方都能获取
        UserHolder.saveUser(userDTO);

        // 8. 刷新Token在Redis中的有效期（实现“滑动窗口”续期）
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 9. 放行请求
        return true;
    }

    /**
     * 在整个请求处理完毕后（视图渲染后）调用
     * 作用: 清理ThreadLocal中的用户信息，防止内存泄漏。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户，避免内存泄漏
        UserHolder.removeUser();
    }
}
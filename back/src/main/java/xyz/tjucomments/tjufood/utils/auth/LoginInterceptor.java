package xyz.tjucomments.tjufood.utils.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.tjucomments.tjufood.dto.UserDTO;
import org.springframework.stereotype.Component;
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查 ThreadLocal 中是否有用户
        if (UserHolder.getUser() == null) {
            // 没有，需要拦截，设置状态码
            response.setStatus(401); // Unauthorized
            return false;
        }
        // 有用户，放行
        return true;
    }
}
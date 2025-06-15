package xyz.tjucomments.tjufood.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.tjucomments.tjufood.interceptor.UserHolder;

/**
 * 登录校验拦截器
 * 作用: 在需要登录的接口执行前，检查当前用户是否已登录。
 * 它的前置拦截器 RefreshTokenInterceptor 已经尝试加载了用户信息。
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在Controller方法执行前调用
     * @return 返回true表示放行，返回false表示拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从UserHolder中尝试获取用户信息
        //    (这些信息是由前一个拦截器 RefreshTokenInterceptor 存入的)
        if (UserHolder.getUser() == null) {
            // 2. 如果获取不到用户，说明用户未登录，没有权限访问此接口
            //    设置HTTP响应状态码为 401 (Unauthorized)
            response.setStatus(401);
            // 3. 拦截请求，不放行
            return false;
        }

        // 4. 如果UserHolder中有用户信息，说明用户已登录，放行请求
        return true;
    }
}
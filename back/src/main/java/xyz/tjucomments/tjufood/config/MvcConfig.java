package xyz.tjucomments.tjufood.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// 更新 import 路径
import xyz.tjucomments.tjufood.interceptor.LoginInterceptor;
import xyz.tjucomments.tjufood.interceptor.RefreshTokenInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private RefreshTokenInterceptor refreshTokenInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器注册顺序决定了它们的执行顺序

//         1. 注册 RefreshTokenInterceptor
        // 它需要拦截所有请求，因为它要为后续的拦截器准备用户信息
        // order(0) 表示它是第一个执行的拦截器
        registry.addInterceptor(refreshTokenInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .order(0);

        // 2. 注册 LoginInterceptor
        // 它只拦截需要登录的接口，所以要排除掉公共接口
        // order(1) 表示它在 RefreshTokenInterceptor 之后执行
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(
                        "/api/users/login",
                        "/api/users/register",
                        "/api/users/password",
                        "/api/verifications/captcha",
                        "/api/verifications/code",
                        "/**/*.html", // 排除静态资源
                        "/**/*.css",
                        "/**/*.js"
                ).order(1);
    }
}
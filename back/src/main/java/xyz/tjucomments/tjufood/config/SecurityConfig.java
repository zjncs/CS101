package xyz.tjucomments.tjufood.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security 配置类
 * 在这个项目中，主要负责提供 PasswordEncoder Bean 和配置基础安全设置。
 * 具体的请求授权逻辑由 MvcConfig 中的拦截器处理。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 提供一个安全的密码加密器 Bean
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // (1) 禁用CSRF保护，因为我们使用Token，是无状态的
                .csrf(AbstractHttpConfigurer::disable)

                // (2) 配置CORS跨域处理
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // (3) 配置Session管理策略为STATELESS（无状态）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // (4) 配置URL的授权规则
                // 我们让Spring Security放行所有请求，因为具体的授权判断
                // 已经交给了 MvcConfig 中的 LoginInterceptor 来处理。
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                .build();
    }

    /**
     * CORS跨域配置
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许所有来源的跨域请求
        configuration.setAllowedOrigins(List.of("*"));
        // 允许所有HTTP方法
        configuration.setAllowedMethods(List.of("*"));
        // 允许所有请求头
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有路径生效
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
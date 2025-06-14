package xyz.tjucomments.tjufood.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 这个 stringRedisTemplate 已经不需要在这里注入了，
    // 因为 RefreshTokenInterceptor 会自己注入它。
    // 你可以暂时保留，或者如果其他地方没用到就可以删除。

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // (1) 禁用CSRF保护
                .csrf(AbstractHttpConfigurer::disable)

                // (2) 配置CORS跨域处理
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

//                // (3) 配置URL的授权规则
//                .authorizeHttpRequests(auth -> auth
                        // (3.1) 对以下公共路径，明确允许所有来源的请求
//                        .requestMatchers(
//                                "/api/users/login",
//                                "/api/users/register",
//                                "/api/users/password",
//                                "/api/verifications/captcha",
//                                "/api/verifications/code"
//                        ).permitAll()
//                        // (3.2) 除了上面放行的路径，其他所有请求都必须经过认证
//                        .anyRequest().authenticated()
//                )
                        // (3) 配置URL的授权规则
                        .authorizeHttpRequests(auth -> auth
                                // 对所有请求都明确允许，无需认证
                                .anyRequest().permitAll()
                        )

                // (4) 配置Session管理策略为STATELESS（无状态）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // (5)  !!! 已经删除错误的代码行 !!!
                // .addFilterBefore(new RefreshTokenInterceptor(stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * CORS配置 Bean
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
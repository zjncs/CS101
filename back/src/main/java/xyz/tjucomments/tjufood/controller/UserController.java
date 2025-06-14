package xyz.tjucomments.tjufood.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import xyz.tjucomments.tjufood.dto.LoginFormDTO;
import xyz.tjucomments.tjufood.dto.PasswordResetDTO;
import xyz.tjucomments.tjufood.dto.RegisterFormDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.service.IUserService;

@Tag(name = "2. 用户核心功能", description = "用户登录、注册、重置密码接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private IUserService userService;

    @Operation(summary = "用户登录", description = "使用账号（邮箱或ID）和密码进行登录，成功后返回Token")
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm) {
        return userService.login(loginForm);
    }

    @Operation(summary = "用户注册", description = "使用邮箱、密码和验证码进行新用户注册")
    @PostMapping("/register")
    public Result register(@RequestBody RegisterFormDTO registerForm) {
        return userService.register(registerForm);
    }

    @Operation(summary = "重置密码", description = "通过邮箱和验证码重置用户密码")
    @PostMapping("/password") // 【修正】根据之前的错误日志，将 @PutMapping 改为 @PostMapping
    public Result resetPassword(@RequestBody PasswordResetDTO resetForm) {
        return userService.resetPassword(resetForm);
    }
}

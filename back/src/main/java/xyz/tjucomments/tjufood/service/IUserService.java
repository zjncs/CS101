package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.*;
import xyz.tjucomments.tjufood.entity.User;

// 继承 IService<User>
public interface IUserService extends IService<User> {
    // 这些都是自定义业务逻辑，全部保留
    Result sendCode(SendCodeDTO sendCodeDTO);
    Result register(RegisterFormDTO registerForm);
    Result login(LoginFormDTO loginForm);
    Result resetPassword(PasswordResetDTO resetForm);
}
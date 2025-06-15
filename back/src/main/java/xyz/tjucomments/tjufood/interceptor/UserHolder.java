package xyz.tjucomments.tjufood.interceptor;

import xyz.tjucomments.tjufood.dto.UserDTO;

/**
 * 使用 ThreadLocal 保存用户信息的工具类
 * 它的代码逻辑保持不变
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
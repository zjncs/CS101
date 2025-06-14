package xyz.tjucomments.tjufood.utils.auth;

import xyz.tjucomments.tjufood.dto.UserDTO;

/**
 * 基于ThreadLocal封装的工具类，用于在当前线程内保存和获取用户信息。
 * 可在拦截器中存入用户，在业务逻辑的任何地方获取，请求结束后自动移除。
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveUser(UserDTO user) {
        THREAD_LOCAL.set(user);
    }

    public static UserDTO getUser() {
        return THREAD_LOCAL.get();
    }

    public static void removeUser() {
        THREAD_LOCAL.remove();
    }
}

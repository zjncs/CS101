package xyz.tjucomments.tjufood.utils.constants;

public class RedisConstants {
    // --- 登录认证相关 ---
    // 登录/注册/密码重置 邮箱验证码
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 5L; // minutes

    // 图形验证码
    public static final String CAPTCHA_CODE_KEY = "captcha:code:";
    public static final Long CAPTCHA_CODE_TTL = 2L; // minutes

    // 用户登录Token (Session)
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L; // minutes

    // --- 缓存相关 ---
    // 缓存空对象TTL
    public static final Long CACHE_NULL_TTL = 2L; // minutes

    // 业务缓存（以商铺为例）
    public static final String CACHE_SHOP_KEY = "cache:shop:";
    public static final Long CACHE_SHOP_TTL = 30L; // minutes

    // 缓存重建分布式锁
    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 10L; // seconds

    public static final Long CACHE_CANTEEN_TTL = 10L;
    public static final String CACHE_CANTEEN_KEY = "canteen";
}

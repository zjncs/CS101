package xyz.tjucomments.tjufood.utils.validation;

/**
 * 正则表达式常量
 */
public abstract class RegexPatterns {
    /**
     * 手机号正则 (适配中国大陆手机号)
     */
    public static final String PHONE_REGEX = "^1([3-9])\\d{9}$";
    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**
     * 密码正则。4~32位的字母、数字、下划线
     */
    public static final String PASSWORD_REGEX = "^\\w{4,32}$";
    /**
     * 验证码正则, 6位数字或字母
     */
    public static final String VERIFY_CODE_REGEX = "^[a-zA-Z\\d]{6}$";
}
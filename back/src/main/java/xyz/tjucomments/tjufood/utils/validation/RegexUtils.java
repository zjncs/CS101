package xyz.tjucomments.tjufood.utils.validation;

import cn.hutool.core.util.StrUtil;

/**
 * 正则校验工具类
 */
public class RegexUtils {
    /**
     * 校验手机号格式是否无效
     * @param phone 要校验的手机号
     * @return true:无效, false:有效
     */
    public static boolean isPhoneInvalid(String phone) {
        return mismatch(phone, RegexPatterns.PHONE_REGEX);
    }

    /**
     * 校验邮箱格式是否无效
     * @param email 要校验的邮箱
     * @return true:无效, false:有效
     */
    public static boolean isEmailInvalid(String email) {
        return mismatch(email, RegexPatterns.EMAIL_REGEX);
    }

    /**
     * 校验验证码格式是否无效
     * @param code 要校验的验证码
     * @return true:无效, false:有效
     */
    public static boolean isCodeInvalid(String code) {
        return mismatch(code, RegexPatterns.VERIFY_CODE_REGEX);
    }

    /**
     * 校验字符串是否不匹配正则表达式
     * @param str 待校验字符串
     * @param regex 正则表达式
     * @return true:不匹配, false:匹配
     */
    private static boolean mismatch(String str, String regex) {
        if (StrUtil.isBlank(str)) {
            return true;
        }
        return !str.matches(regex);
    }
}
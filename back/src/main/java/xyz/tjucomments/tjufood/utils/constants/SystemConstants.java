// --- 文件: utils/constants/SystemConstants.java ---
package xyz.tjucomments.tjufood.utils.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemConstants {

    @Value("${tjufood.image-upload-dir}") // 从配置文件读取
    public String imageUploadDir;

    public static final String USER_NICK_NAME_PREFIX = "user_";
    public static final int DEFAULT_PAGE_SIZE = 5;
    public static final int MAX_PAGE_SIZE = 10;
}

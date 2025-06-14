package xyz.tjucomments.tjufood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.tjucomments.tjufood.entity.User;

/**
 * 用户Mapper接口 (已重写并适配 MyBatis-Plus)
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // ========== MyBatis-Plus 提供的通用方法 (无需编写) ==========
    // int insert(User user);              --> insertUser() 的替代
    // User selectById(Long id);           --> queryById() 的替代
    // List<User> selectByMap(Map<String, Object> columnMap); --> queryByEmail() 的一种替代方式
    // ...以及更多

    // ========== 保留的自定义查询/更新方法 ==========
    // 这些方法逻辑比较特殊，建议保留，并在对应的XML文件中实现

    /**
     * 根据邮箱和密码更新密码
     * (这是一个特定的更新操作，不同于 updateById，建议保留)
     * @param email 邮箱
     * @param password 新密码
     * @return 影响的行数
     */
    Integer updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

    // queryByEmail 和 queryById 方法已被 BaseMapper 中的 selectOne 和 selectById 完美替代，
    // 在 Service 层中可以这样调用:
    // User user = userMapper.selectOne(new QueryWrapper<User>().eq("email", "xxx@xxx.com"));
    // User user = userMapper.selectById(1L);
    // 如果你觉得自定义方法更直观，也可以保留 queryByEmail。
}
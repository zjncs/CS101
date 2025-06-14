package xyz.tjucomments.tjufood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.dto.StallDTO;
import java.util.List;

/**
 * 窗口Mapper接口 (已重写并适配 MyBatis-Plus)
 */
@Mapper
public interface StallMapper extends BaseMapper<Stall> {

    // ========== MyBatis-Plus 提供的通用方法 (无需编写) ==========
    // insert(), deleteById(), updateById(), selectById(), selectList() ...
    // 已替代了原有的 insertStall, updateStall, deleteStall 方法

    // ========== 保留的自定义查询方法 (返回 DTO) ==========
    // 这些方法通常涉及多表连接，需要自定义SQL，因此予以保留

    /**
     * 根据ID查询窗口详情 (返回DTO)
     * @param id 窗口ID
     * @return 窗口DTO
     */
    StallDTO queryStallById(Long id);

    /**
     * 根据食堂ID查询其下所有窗口 (返回DTO列表)
     * @param canteenId 食堂ID
     * @return 窗口DTO列表
     */
    List<StallDTO> listStallsByCanteenId(@Param("canteenId") Long canteenId);
}

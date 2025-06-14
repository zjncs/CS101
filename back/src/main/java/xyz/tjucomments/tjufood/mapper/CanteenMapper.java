package xyz.tjucomments.tjufood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.dto.CanteenDTO;
import java.util.List;

/**
 * 食堂Mapper接口 (已重写并适配 MyBatis-Plus)
 */
@Mapper
public interface CanteenMapper extends BaseMapper<Canteen> {

    // ========== MyBatis-Plus 提供的通用方法 (无需编写) ==========
    // insert(), deleteById(), updateById(), selectById(), selectList() ...
    // 已替代了原有的 insertCanteen, updateCanteen, deleteCanteen 方法

    // ========== 保留的自定义查询方法 (返回 DTO) ==========
    // 这些方法通常涉及多表连接，需要自定义SQL，因此予以保留

    /**
     * 查询食堂列表 (返回包含额外信息的DTO)
     * @return 食堂DTO列表
     */
    List<CanteenDTO> listCanteens();

    /**
     * 根据ID查询单个食堂详情 (返回包含额外信息的DTO)
     * @param id 食堂ID
     * @return 食堂DTO
     */
    CanteenDTO queryCanteenById(Long id);
}
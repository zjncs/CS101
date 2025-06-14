package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Canteen;

// 继承 IService<Canteen>，自动获得大量通用CRUD接口
public interface ICanteenService extends IService<Canteen> {
    /**
     * 查询食堂列表 (返回DTO)
     * (因为返回的是DTO，且涉及多表查询，所以保留此自定义方法)
     * @return Result
     */
    Result listCanteens();

    /**
     * 根据ID查询食堂详情 (返回DTO)
     * (同上，保留此方法)
     * @param id 食堂ID
     * @return Result
     */
    Result queryCanteenById(Long id);

    // addCanteen, updateCanteen, deleteCanteen 等方法已由 IService 的标准方法 save, updateById, removeById 替代，
    // 其业务逻辑已移至 Controller 层，故此处无需定义。
}

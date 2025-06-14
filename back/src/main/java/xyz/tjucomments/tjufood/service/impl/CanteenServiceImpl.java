package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import xyz.tjucomments.tjufood.dto.CanteenDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Canteen;
import xyz.tjucomments.tjufood.mapper.CanteenMapper;
import xyz.tjucomments.tjufood.service.ICanteenService;
import xyz.tjucomments.tjufood.utils.cache.CacheClient;
import xyz.tjucomments.tjufood.utils.constants.RedisConstants;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
// 继承 ServiceImpl<Mapper, Entity>，自动注入 baseMapper 并获得大量CRUD实现
public class CanteenServiceImpl extends ServiceImpl<CanteenMapper, Canteen> implements ICanteenService {

    @Resource
    private CacheClient cacheClient;
    // baseMapper (即 CanteenMapper) 已由 ServiceImpl 自动注入

    @Override
    public Result listCanteens() {
        // 调用自定义的XML查询，保持不变
        List<CanteenDTO> canteens = baseMapper.listCanteens();
        return Result.ok(canteens);
    }

    @Override
    public Result queryCanteenById(Long id) {
        // 缓存逻辑保持不变，其内部调用的db查询是自定义的XML查询，所以不受影响
        CanteenDTO canteen = cacheClient.queryWithPassThrough(
                RedisConstants.CACHE_CANTEEN_KEY, // 建议为食堂定义新的常量
                id,
                CanteenDTO.class,
                canteenId -> baseMapper.queryCanteenById(canteenId), // 调用的是自定义方法
                RedisConstants.CACHE_CANTEEN_TTL, // 建议为食堂定义新的常量
                TimeUnit.MINUTES
        );
        if (canteen == null) {
            return Result.fail("食堂不存在！");
        }
        return Result.ok(canteen);
    }

    // 【已移除】以下方法已不再需要在 ServiceImpl 中实现，
    // 因为 Controller 层会直接调用 IService 提供的 save(), updateById(), removeById() 方法。
    // - addCanteen(Canteen canteen)
    // - updateCanteen(Canteen canteen)
    // - deleteCanteen(Long id)
}
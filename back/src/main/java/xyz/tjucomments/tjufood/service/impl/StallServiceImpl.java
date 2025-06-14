package xyz.tjucomments.tjufood.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.dto.StallDTO;
import xyz.tjucomments.tjufood.entity.Stall;
import xyz.tjucomments.tjufood.mapper.StallMapper;
import xyz.tjucomments.tjufood.service.IStallService;
import java.util.List;

@Service
public class StallServiceImpl extends ServiceImpl<StallMapper, Stall> implements IStallService {
    // baseMapper 已由 ServiceImpl 自动注入

    @Override
    public Result queryStallById(Long id) {
        // 调用自定义XML查询，保持不变
        StallDTO stall = baseMapper.queryStallById(id);
        if (stall == null) {
            return Result.fail("窗口不存在！");
        }
        return Result.ok(stall);
    }

    @Override
    public Result listStallsByCanteenId(Long canteenId) {
        // 调用自定义XML查询，保持不变
        List<StallDTO> stalls = baseMapper.listStallsByCanteenId(canteenId);
        return Result.ok(stalls);
    }

    @Override
    @Transactional
    public Result addStall(Stall stall) {
        boolean isSuccess = save(stall);
        return isSuccess ? Result.ok("新增窗口成功，ID为：" + stall.getId()) : Result.fail("新增窗口失败！");
    }

    @Override
    @Transactional
    public Result updateStall(Stall stall) {
        if (stall.getId() == null) {
            return Result.fail("更新失败，窗口ID不能为空");
        }
        boolean isSuccess = updateById(stall);
        return isSuccess ? Result.ok("更新窗口成功") : Result.fail("更新窗口失败！");
    }

    @Override
    @Transactional
    public Result deleteStall(Long id) {
        boolean isSuccess = removeById(id);
        return isSuccess ? Result.ok("删除窗口成功") : Result.fail("删除窗口失败！");
    }
}

package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.Voucher;

import java.util.List;

@Mapper
public interface VoucherMapper {
    @Select("select * from tb_voucher where status in (0,1) order by start_time desc")
    List<Voucher> listActive();

    @Select("select * from tb_voucher where id = #{id}")
    Voucher findById(long id);
}

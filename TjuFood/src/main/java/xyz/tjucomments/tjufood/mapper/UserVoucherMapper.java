package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.UserVoucher;

import java.util.List;

@Mapper
public interface UserVoucherMapper {
    @Select("select * from tb_user_voucher where user_id = #{userId} order by create_time desc")
    List<UserVoucher> findByUser(long userId);
}
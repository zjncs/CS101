package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.tjucomments.tjufood.entity.CreditRecord;

import java.util.List;

@Mapper
public interface CreditRecordMapper {
    @Select("select * from tb_credit_record where user_id = #{userId} order by create_time desc limit 50")
    List<CreditRecord> findByUser(long userId);
}

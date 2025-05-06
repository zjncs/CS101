package xyz.tjucomments.tjufood.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import xyz.tjucomments.tjufood.entity.Account;
import xyz.tjucomments.tjufood.entity.AccountUser;

@Mapper
public interface UserMapper {
    @Select("select * from tb_user where username = #{text} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Select("select * from tb_user where username = #{text} or email = #{text}")
    AccountUser findAccountUserByNameOrEmail(String text);

    @Insert("insert into tb_user (email, username, password) values (#{email}, #{username}, #{password})")
    int createAccount(String username, String password, String email);

    @Update("update tb_user set password = #{password} where email = #{email}")
    int resetPasswordByEmail(String password, String email);
}
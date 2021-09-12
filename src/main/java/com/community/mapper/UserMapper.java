package com.community.mapper;

import com.community.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /*
     *id自增长
     */
    @Insert("insert into user (name,accountId,token,create_time,update_time) values (#{name},#{accountId},#{token},#{createTime},#{updateTime})")
    public int insert(User user);

    @Select("select * from user")
    public List<User> select();

    @Delete("delete from user where id = #{id}")
    public int deleteById(int id);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where id = #{id}")
    User findById(int id);

    @Update(("update user set name = #{name} where id = #{id}"))
    int updateUserNameById(int id);

    @Select("select count(*) from user where accountId = #{accountId}")
    int getAccountIdCount(int accountId);

    @Update("update user set token = #{newToken} where accountId = #{accountId}")
    void updateTokenByAccountId(String newToken,int accountId);

    @Select("select name from user where id = #{id}")
    String findUserNameById(int id);

    @Select("select avatar from user where id =#{id}")
    String findAvatarById(int id);

    @Select("select count(*) from user")
    int findUserCount();
}

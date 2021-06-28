package com.community.mapper;

import com.community.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    /*
     *id自增长
     */
    @Insert("insert into user (name,accountId,token,create_time,update_time) values (#{name},#{accountId},#{token},#{create_time},#{update_time})")
    public int insert(User user);

    @Select("select * from user")
    public List<User> select();

    @Delete("delete from user where id = #{id}")
    public int deleteById(int id);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where id = #{id}")
    User findById(int id);
}

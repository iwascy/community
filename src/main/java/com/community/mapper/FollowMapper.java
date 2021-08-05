package com.community.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FollowMapper {

    @Insert("insert into follow (user,user_followed,create_time,update_time) values (#{user},#{userFollowed},#{createTime},#{updateTime})")
    void addFollow(int user,int userFollowed,long createTime,long updateTime);

    @Select("select count(*) from follow where user = #{user} and user_followed = #{userFollowed}")
    int  findFollowStatus(int user,int userFollowed);

    @Delete("delete from follow where user = #{user} and user_followed = #{userFollowed}")
    void deleteFollow(int user,int userFollowed);
}

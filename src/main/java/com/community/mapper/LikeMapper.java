package com.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface LikeMapper {

    @Insert("insert into like (user,question,create_time,update_time) values (#{user},#{question},#{createTime},#{updateTime})")
    public void addLike(int user,int question,long createTime,long updateTime);

    @Select("select count(*) from like where user = #{user},question = #{question}")
    public int findLikeCountByUserAndQuestion(int user,int question);
}

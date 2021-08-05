package com.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

@Mapper
public interface PraiseMapper {

    @Insert("insert into praise (user,question,create_time,update_time) values (#{user},#{question},#{createTime},#{updateTime})")
    void addPraise(int user,int question,long createTime,long updateTime);

    @Select("select count(*) from praise where user = #{user} and question = #{question}")
    int findPraiseCountByUserAndQuestion(int user,int question);

    @Delete("delete from praise where user = #{user} and question = #{question}")
    void deletePraiseByUserAndQuestion(int user,int question);
}

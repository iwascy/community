package com.community.mapper;

import com.community.domain.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectMapper {

    @Select("select * from collect where account_id = #{accountId}")
    List<Collect> findCollectByAccountId(int accountId);
}

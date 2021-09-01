package com.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhotoMapper {

    @Insert("insert into photo (question_id,photo_url,cos_key) values (#{questionId},#{photoUrl},#{cosKey})")
    public void insertPhoto(int questionId,String photoUrl,String cosKey);
}

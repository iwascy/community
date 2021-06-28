package com.community.mapper;

import com.community.domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,detail,create_time,update_time,creator,tag) values (#{title},#{detail},#{createTime},#{updateTime},#{creator},#{tag})")
    int insert(Question question);

    @Select("select * from question")
    List<Question> findAllQuestion();

    @Select("select title from questioin")
    List<String> findAllTitle();

    @Select("select comment_count from question where id = #{id}")
    int findCommentCount(int id);

    @Select("select view_count from question where id = #{id}")
    int findViewCount(int id);

    @Select("select like_count from question where id = #{id}")
    int findLikeCount(int id);

    @Select("select detail from question where id = #{id}")
    String findDetail(int id);

    @Select("select * from questioin where creator = #{creator}")
    List<Question> findQuestionsByCreator(int creator);
}

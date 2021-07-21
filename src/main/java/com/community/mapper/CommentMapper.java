package com.community.mapper;

import com.community.domain.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Entity com.community.domain.Comment
 */
@Mapper
public interface CommentMapper {

    int deleteByPrimaryKey(Long id);

    @Insert("insert into comment (question_id,type,commentator,create_time,"+
            "update_time,content) values (#{questionId},#{type},#{commentator},#{createTime},#{updateTime},#{content})")
    int insert(Comment comment);

    int insertSelective(Comment comment);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment comment);

    int updateByPrimaryKey(Comment comment);

    @Select("select * from comment where question_id = #{questionId}")
    List<Comment> findCommentsByQuestionId(int questionId);

    @Select("select count(*) from question where id = #{id}")
    int findQuestionCountById(int id);

    @Select("udpate comment set like_count = like_count+1 where id = #{id}")
    void addLike(int id);

    @Select("select count(*) from comment where type = #{commentId")
    int findReplyCount(int commentId);

    @Select("select count(*) from comment where question_id = #{questionId} and type = #{type}")
    int getReplyCount(int questionId,int type);
}





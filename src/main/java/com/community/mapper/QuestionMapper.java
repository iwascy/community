package com.community.mapper;

import com.community.domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,detail,create_time,update_time,creator,tag) values (#{title},#{detail},#{createTime},#{updateTime},#{creator},#{tag})")
    int insert(Question question);

    @Select("select * from question")
    List<Question> findAllQuestions();

    @Select("select * from question where id = #{id}")
    Question findQuestionById(int id);

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

    @Select("select * from question where creator = #{creator}")
    List<Question> findQuestionsByCreator(int creator);

    @Select("select count(*) from question")
    int questionCount();

    @Select("select * from question where tag = #{tag}")
    List<Question> questionOfTag(String tag);

    @Update("update question set title = #{title},tag = #{tag},detail = #{detail},update_time = #{updateTime} where id = #{id}")
    void updateQuestionById(String title,String tag,String detail,long updateTime,int id);

    @Update("update question set view_count = view_count +1 where id = #{id}")
    void addViewCount(int id);

    @Update("update question set comment_count = comment_count +1 where id = #{id}")
    void addCommentCount(long id);

    /*
    按最新修改时间排序
     */
    @Select("select * from question order by update_time desc")
    List<Question> sortByLatestTime();

    @Select("select * from question order by comment_count DESC")
    List<Question> sortByCommentCount();

    /*
    修改问题时间
     */
    @Select("update question set update_time = #{updateTime} where id = #{questionId}")
    void setUpdateTime(long updateTime,int questionId);

    /*
    增加一个点赞数
     */
    @Update("update question set like_count = like_count+1 where id = #{id}")
    void addLikeCount(int id);

    /*
    删除一个点赞数
     */
    @Update("update question set like_count = like_count-1 where id = #{id}")
    void reduceLikeCount(int id);
}

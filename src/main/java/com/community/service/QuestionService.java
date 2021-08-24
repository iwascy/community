package com.community.service;

import com.community.domain.Comment;
import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.IndexQuestionDTO;
import com.community.dto.QuestionDTO;
import com.community.dto.ShowCommentDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentService commentService;


    public void PublishQuestion(User user,String title,String detail,String tag){
        Question question = new Question();
        question.setCreator(user.getId());
        question.setTitle(title);
        question.setDetail(detail);
        question.setTag(tag);
        question.setCreateTime(System.currentTimeMillis());
        question.setUpdateTime(question.getCreateTime());
        questionMapper.insert(question);
    }

    public List<IndexQuestionDTO> questionIndex(){

        List<IndexQuestionDTO> indexQuestionDTOList = new ArrayList<>();

        return indexQuestionDTOList;
    }

    public void addView(int id) {
        questionMapper.addViewCount(id);
    }

    public List<Question> sortByLatestTime(){
        return questionMapper.sortByLatestTime();
    }

    public List<Question> sortByPopular(){
        return questionMapper.sortByCommentCount();
    }

    public List<Question> findQuestionByUser(int id){
        return questionMapper.findQuestionsByCreator(id);
    }


    public void addPraiseCount(int questionId){
        questionMapper.addPraiseCount(questionId);
    }

    public void reducePraiseCount(int questionId){
        questionMapper.reducePraiseCount(questionId);
    }

    public void delete(int questionId) {
        questionMapper.deleteById(questionId);
    }

    public List<Question> findQuestionByFollow(int user){
        return questionMapper.findQuestionByFollow(user);
    }

    public int praiseCountById(int id){
        return questionMapper.findPraiseCount(id);
    };

    public QuestionDTO showQuestion(int id){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(id);
        int creatorId = questionMapper.findCreatorByQuestionId(id);
        questionDTO.setAuthor(userMapper.findUserNameById(creatorId));
        questionDTO.setTitle(questionMapper.findTitleById(id));
        questionDTO.setDetail(questionMapper.findDetail(id));
        questionDTO.setTime(questionMapper.findTimeByQuestionId(id));
        questionDTO.setAvatar(userMapper.findAvatarById(creatorId));
        questionDTO.setCreatorId(creatorId);
        questionDTO.setCommentCount(questionMapper.findCommentCount(id));
        List<ShowCommentDTO> firstShowCommentDTOList = new ArrayList<>();
        List<ShowCommentDTO> secondShowCommentDTOList = new ArrayList<>();
        List<Comment> commentList = commentService.findQuestionComment(id);
        for (Comment comment : commentList) {
            if(comment.getType()==0){
                ShowCommentDTO firstShowCommentDTO = new ShowCommentDTO();
                firstShowCommentDTO.setCommentUser(userMapper.findUserNameById(comment.getCommentator()));
                firstShowCommentDTO.setContent(comment.getContent());
                firstShowCommentDTO.setAvatar(userMapper.findAvatarById(comment.getCommentator()));
                firstShowCommentDTO.setTime(comment.getCreateTime());
                firstShowCommentDTO.setType(comment.getType());
                firstShowCommentDTO.setCommentUserId(comment.getCommentator());
                firstShowCommentDTO.setCommentId(comment.getId());
                firstShowCommentDTOList.add(firstShowCommentDTO);
            }else{
                ShowCommentDTO secondShowCommentDTO = new ShowCommentDTO();
                secondShowCommentDTO.setCommentUser(userMapper.findUserNameById(comment.getCommentator()));
                secondShowCommentDTO.setContent(comment.getContent());
                secondShowCommentDTO.setAvatar(userMapper.findAvatarById(comment.getCommentator()));
                secondShowCommentDTO.setTime(comment.getCreateTime());
                secondShowCommentDTO.setType(comment.getType());
                secondShowCommentDTO.setCommentUserId(comment.getCommentator());
                secondShowCommentDTO.setCommentId(comment.getId());
                secondShowCommentDTOList.add(secondShowCommentDTO);
            }

        }
        questionDTO.setFirstShowCommentDTOList(firstShowCommentDTOList);
        questionDTO.setSecondShowCommentDTOList(secondShowCommentDTOList);
        return questionDTO;
    }



}

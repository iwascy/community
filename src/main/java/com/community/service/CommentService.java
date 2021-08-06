package com.community.service;

import com.community.domain.Comment;
import com.community.dto.CommentDTO;
import com.community.enums.NotificationEnum;
import com.community.mapper.CommentMapper;
import com.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public void addComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setQuestionId(commentDTO.getQuestionId());
        comment.setCommentator(commentDTO.getCommentator());
        comment.setContent(commentDTO.getContent());
        comment.setCreateTime(System.currentTimeMillis());
        comment.setUpdateTime(comment.getCreateTime());
        comment.setType(commentDTO.getType());
        commentMapper.insert(comment);
        questionMapper.setUpdateTime(comment.getCreateTime(),comment.getQuestionId());
        questionMapper.addCommentCount(commentDTO.getQuestionId());
        int userCommented = questionMapper.findCreatorByQuestion(commentDTO.getQuestionId());
        notificationService.addNotification(comment.getCommentator(),userCommented, NotificationEnum.REPLY_QUESTION.getType(), commentDTO.getQuestionId());
    }

    public List<Comment> findQuestionComment(int questionId){
        return commentMapper.findCommentsByQuestionId(questionId);
    }

    public int questionCountById(int id){
        return commentMapper.findQuestionCountById(id);
    }

    public void addLike(int id){
        commentMapper.addLike(id);
    }

    public int getCommentReplyCount(int id){
        return commentMapper.findReplyCount(id);
    }

    public int getReplyCount(int questionId,int type){
        return commentMapper.getReplyCount(questionId,type);
    };
}

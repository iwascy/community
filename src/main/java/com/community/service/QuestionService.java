package com.community.service;

import com.community.CommunityApplication;
import com.community.domain.Comment;
import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.IndexQuestionDTO;
import com.community.dto.QuestionDTO;
import com.community.dto.QuestionProfileDTO;
import com.community.dto.ShowCommentDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    protected static final Logger logger = LoggerFactory.getLogger(CommunityApplication.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentService commentService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;


    public int addQuestionViewCount(int questionId){
        String key = "question:" + questionId;
        String viewCountString = "viewCount";
        boolean hasKey = redisTemplate.hasKey(key);
        int viewCount = 0;
        if(hasKey && redisTemplate.opsForHash().hasKey(key,viewCountString)){
            redisTemplate.opsForHash().increment(key,viewCountString,1);
            viewCount = (int) redisTemplate.opsForHash().get(key,viewCountString);
        }else{
            int count = 0;
            try{
                count = questionMapper.findViewCount(questionId);
            }catch (Exception e){
                logger.info("找不到问题，"+e);
            }
            redisTemplate.opsForHash().put(key,viewCountString,count+1);
            viewCount = count;
        }
        return viewCount;
    }


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

    public PageInfo sortByLatestTime(){
        PageHelper.startPage(1,5);
        List<Question> questionList = questionMapper.sortByLatestTime();
        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(questionList);
        for (Question question : (List<Question>) pageInfo.getList()) {
            QuestionProfileDTO questionProfileDTO = new QuestionProfileDTO();
            questionProfileDTO.setId(question.getId());
            questionProfileDTO.setName(userService.findUserNameById(question.getCreator()));
            questionProfileDTO.setCommentCount(question.getCommentCount());
            questionProfileDTO.setPraiseCount(questionProfileDTO.getPraiseCount());
            questionProfileDTO.setTitle(question.getTitle());
            int len = question.getDetail().length();
            if(len > 100){
                questionProfileDTO.setDetail(question.getDetail().substring(1,100)+"......");
            }else {
                questionProfileDTO.setDetail(question.getDetail());
            }

            questionProfileDTO.setTag(questionProfileDTO.getTag());
            questionProfileDTOList.add(questionProfileDTO);
        }
        pageInfo.setList(questionProfileDTOList);
        return pageInfo;
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

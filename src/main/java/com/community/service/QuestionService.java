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
import java.util.concurrent.TimeUnit;

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
    private QuestionConvert questionConvert;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private PraiseService praiseService;


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
            redisTemplate.expire(key,2,TimeUnit.HOURS);
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

        return praiseService.getPraiseCount(id);
    };

    public QuestionDTO showQuestion(int id){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(id);
        int creatorId = questionMapper.findCreatorByQuestionId(id);
        questionDTO.setViewCount(addQuestionViewCount(id));
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




    public void setPopularQuestionDbToRedis(){
        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        List<Question> questionList = questionMapper.sortByCommentCount();
        for (Question question : questionList) {
            QuestionProfileDTO questionProfileDTO = new QuestionProfileDTO();
            questionProfileDTO.setId(question.getId());
            questionProfileDTO.setName(userService.findUserNameById(question.getCreator()));
            questionProfileDTO.setCommentCount(question.getCommentCount());
            questionProfileDTO.setPraiseCount(question.getPraiseCount());
            questionProfileDTO.setViewCount(question.getViewCount());
            questionProfileDTO.setTitle(question.getTitle());
            questionProfileDTO.setTag(question.getTag());
            int len = question.getDetail().length();
            if (len > 100) {
                questionProfileDTO.setDetail(question.getDetail().substring(1, 100) + "......");
            } else {
                questionProfileDTO.setDetail(question.getDetail());
            }
            questionProfileDTO.setTag(questionProfileDTO.getTag());
            questionProfileDTOList.add(questionProfileDTO);
        }
        int i = 1;
        for (QuestionProfileDTO questionProfileDTO : questionProfileDTOList) {
            String key = "question:popular:" + i;
            redisTemplate.boundHashOps(key).put("id", questionProfileDTO.getId());
            redisTemplate.boundHashOps(key).put("name", questionProfileDTO.getName());
            redisTemplate.boundHashOps(key).put("title", questionProfileDTO.getTitle());
            redisTemplate.boundHashOps(key).put("detail", questionProfileDTO.getDetail());
            redisTemplate.boundHashOps(key).put("commentCount", questionProfileDTO.getCommentCount());
            redisTemplate.boundHashOps(key).put("viewCount", questionProfileDTO.getViewCount());
            redisTemplate.boundHashOps(key).put("praiseCount", questionProfileDTO.getPraiseCount());
            redisTemplate.boundHashOps(key).put("tag", questionProfileDTO.getTag());
            redisTemplate.expire(key, 2, TimeUnit.HOURS);
            i++;
        }
    }

    public List<QuestionProfileDTO> getPopularQuestionFromRedis(){
        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        if(!redisTemplate.hasKey("question:popular:1")){
            setPopularQuestionDbToRedis();
        }
        for (int i = 1; i <= 10; i++) {
            String key = "question:popular:" + i;
            QuestionProfileDTO questionProfileDTO = new QuestionProfileDTO();
            questionProfileDTO.setName((String) redisTemplate.boundHashOps(key).get("name"));
            questionProfileDTO.setId((Integer) redisTemplate.boundHashOps(key).get("id"));
            questionProfileDTO.setTitle((String) redisTemplate.boundHashOps(key).get("title"));
            questionProfileDTO.setDetail((String) redisTemplate.boundHashOps(key).get("detail"));
            questionProfileDTO.setCommentCount((Integer) redisTemplate.boundHashOps(key).get("commentCount"));
            questionProfileDTO.setViewCount((Integer) redisTemplate.boundHashOps(key).get("viewCount"));
            questionProfileDTO.setPraiseCount((Integer) redisTemplate.boundHashOps(key).get("praiseCount"));
            questionProfileDTO.setTag((String) redisTemplate.boundHashOps(key).get("tag"));
            questionProfileDTOList.add(questionProfileDTO);
        }
        return questionProfileDTOList;
    }
}

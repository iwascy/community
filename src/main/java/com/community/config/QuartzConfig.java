package com.community.config;
import com.community.job.SyncPopularQuestion;
import com.community.job.SyncPopularQuestion;
import com.community.service.QuestionService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz定时任务配置类
 * @author pan_junbiao
 **/
@Configuration
public class QuartzConfig
{
    private static String JOB_GROUP_NAME = "PJB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "PJB_TRIGGERGROUP_NAME";


    /**
     * 定时任务1：
     * 将数据库中热榜同步到redis（任务详情）
     */
    @Bean
    public JobDetail syncPopularQuestionJobDetail()
    {
        JobDetail jobDetail = JobBuilder.newJob(SyncPopularQuestion.class)
                .withIdentity("syncPopularQuestionJobDetail",JOB_GROUP_NAME)
                .usingJobData("detail", "将问题热榜存入redis") //设置参数（键值对）
                .storeDurably() //即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
        return jobDetail;
    }


    /**
     * 同步问题热榜触发器
     * @return
     */
    @Bean
    public Trigger syncQuestionJobTrigger()
    {
        //每隔2小时执行一次
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0/2 * * ?");

        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(syncPopularQuestionJobDetail())//关联上述的JobDetail
                .withIdentity("syncQuestionJobTrigger",TRIGGER_GROUP_NAME)//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }
}
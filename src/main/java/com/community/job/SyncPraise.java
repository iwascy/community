package com.community.job;
import com.community.service.PraiseService;
import com.community.service.QuestionService;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 同步点赞Job
 * @author pan_junbiao
 **/
public class SyncPraise extends QuartzJobBean
{
    @Autowired
    private PraiseService praiseService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
    {
        //获取JobDetail中传递的参数
        String detail = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("detail");
        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //任务
        praiseService.redisToDb();
        //打印信息
        System.out.println("----------------------------------------");
        System.out.println("任务：" + detail);
        System.out.println("当前时间：" + dateFormat.format(date));
        System.out.println("----------------------------------------");
    }
}
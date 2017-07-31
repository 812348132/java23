package com.kaishengit.crm.service.impl;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.quartz.jobs.WeixinJob;
import org.apache.commons.lang3.StringUtils;
import static com.cronutils.model.field.expression.FieldExpressionFactory.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.cronutils.model.definition.CronDefinitionBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.cronutils.model.field.expression.FieldExpressionFactory.on;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 新增任务
     * @param task
     */
    @Override
    @Transactional
    public void saveTask(Task task) {
        task.setCreateTime(new Date());
        task.setDone(false);
        taskMapper.saveTask(task);

        //判断是否有提醒时间
        if(StringUtils.isNotEmpty(task.getAlertTime())) {
            //添加参数
            JobDataMap jobDataMap = new JobDataMap();
           /* jobDataMap.put("to",task.getAccountId());
            jobDataMap.put("message",task.getTitle());*/
           jobDataMap.putAsString("to",task.getAccountId());
           jobDataMap.put("message",task.getTitle());

            //调度器
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            //创建job
            JobDetail jobDetail = JobBuilder.newJob(WeixinJob.class).setJobData(jobDataMap)
                    .withIdentity(new JobKey("taskId" + task.getId(),"weixinGroup")).build();
            //字符串日期格式转换为JodaTime的DateTime类对象
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            DateTime dateTime = formatter.parseDateTime(task.getAlertTime());
            //根据日期生成cron表达式
            Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ))
                    .withYear(on(dateTime.getYear()))
                    .withMonth(on(dateTime.getMonthOfYear()))
                    .withDoM(on(dateTime.getDayOfMonth()))
                    .withHour(on(dateTime.getHourOfDay()))
                    .withMinute(on(dateTime.getMinuteOfHour()))
                    .withSecond(on(dateTime.getSecondOfMinute()))
                    .withDoW(questionMark())
                    .instance();
            String cronExpression = cron.asString();
            //创建触发器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            try {
                scheduler.scheduleJob(jobDetail, trigger);
                scheduler.start();
            } catch (SchedulerException ex) {
                throw  new ServiceException("添加定时任务异常",ex);
            }
        }
    }

    @Override
    public List<Task> findTaskByCustId(Integer id) {
        return taskMapper.findTaskByCustId(id);
    }

    @Override
    public List<Task> findTaskBySalesId(Integer id) {
        return taskMapper.findTaskBySalesId(id);
    }


    @Override
    public Task findAllTaskById(Integer id) {
        return taskMapper.findAllTaskById(id);
    }

    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
    }

    @Override
    public List<Task> findAllTaskByAccountId(Integer id, boolean showAll) {
        return taskMapper.findAllTaskByAccountId(id,showAll);
    }

    @Override
    public Task findTaskById(Integer id) {
        return taskMapper.findById(id);
    }

    @Override
    @Transactional
    public void delTask(Task task) {
        //判断删除的Task是否有提醒时间，如果有则删除定时任务
        if(StringUtils.isNotEmpty(task.getAlertTime())) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskId:" + task.getId(), "weixinGroup"));
            } catch (SchedulerException ex) {
                throw new ServiceException("删除调度器任务异常",ex);
            }
        }
        taskMapper.delTask(task);
    }

}

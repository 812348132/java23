package com.kaishengit.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WeixinJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        Integer toUser = dataMap.getIntegerFromString("to");
        String message = (String) dataMap.get("message");
        System.out.println("发送微信通知~~~~~~~~~~~~~~~~~~~~~~~" + toUser + " : " + message);
    }
}


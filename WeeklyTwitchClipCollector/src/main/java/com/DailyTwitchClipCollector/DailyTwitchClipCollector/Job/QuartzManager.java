package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzManager {
    SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler;

    public void sceduleJob(JobDetail job, Trigger trigger){
        try{
            this.scheduler = schedulerFactory.getScheduler();
            this.scheduler.scheduleJob(job, trigger);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try{
            this.scheduler.start();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }
}

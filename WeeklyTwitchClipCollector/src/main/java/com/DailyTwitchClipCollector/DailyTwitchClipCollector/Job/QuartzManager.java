package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

public class QuartzManager {
    @Autowired
    SchedulerFactory schedulerFactory;

    private Scheduler scheduler;

    public QuartzManager(){
        try{
            this.scheduler = schedulerFactory.getScheduler();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sceduleJob(JobDetail job, Trigger trigger){
        try{
            this.scheduler.scheduleJob(job, trigger);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import org.springframework.beans.factory.annotation.Autowired;

public class JobService {

    @Autowired
    QuartzManager quartzManager;

    @Autowired
    DailyJobService dailyJobService;
    public void initJob(){
        quartzManager.sceduleJob(dailyJobService.createDailyJob(),dailyJobService.createTrigger());
    }
}

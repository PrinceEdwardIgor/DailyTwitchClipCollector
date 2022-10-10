package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util.Dict;
import org.quartz.*;

public class DailyJobService {
    public JobDetail createDailyJob(){
        return JobBuilder.newJob(DailyJob.class).
                        withIdentity("dailyTwitchClipCollector", "dailyJob").build();
    }

    public CronTrigger createTrigger(){
        return TriggerBuilder.newTrigger()
                .withIdentity("dailyTwitchClipCollectTrigger", "dailyTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(Dict.DAILY_CRON))
                .build();
    }
}

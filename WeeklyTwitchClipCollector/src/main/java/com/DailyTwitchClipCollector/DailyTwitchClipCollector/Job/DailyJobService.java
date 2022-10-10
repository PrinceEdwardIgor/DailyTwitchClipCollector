package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util.Dict;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
public class DailyJobService {
    public JobDetail createDailyJob(){
        return JobBuilder.newJob(DailyJob.class).
                        withIdentity("dailyTwitchClipCollector", "dailyJob").build();
    }

    public CronTrigger createTrigger(){
        return TriggerBuilder.newTrigger()
                .withIdentity("dailyTwitchClipCollectTrigger", "dailyTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(Dict.TEST_CRON))
                .build();
    }
}

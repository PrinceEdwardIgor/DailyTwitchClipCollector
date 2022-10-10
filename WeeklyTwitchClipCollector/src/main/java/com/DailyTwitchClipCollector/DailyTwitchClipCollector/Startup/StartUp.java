package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Startup;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class StartUp implements CommandLineRunner {
    @Autowired
    JobService jobService;
    @Override
    public void run(String... args) throws Exception {
        jobService.initJob();
    }
}

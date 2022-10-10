package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Startup;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order
@Slf4j
public class StartUp implements CommandLineRunner {
    @Autowired
    JobService jobService;
    @Override
    public void run(String... args){
        jobService.initJob();
    }
}

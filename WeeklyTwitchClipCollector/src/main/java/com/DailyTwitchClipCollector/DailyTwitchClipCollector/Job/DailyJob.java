package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.GetTwitchClipResponseData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Services.TwitchClipDownloadService;
import com.google.gson.Gson;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class DailyJob implements Job {

    @Autowired
    private TwitchClipDownloadService twitchClipDownloadService;

    Gson gson = new Gson();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String jsonResponse = twitchClipDownloadService.getDailyTwitchClip();
        GetTwitchClipResponseData getTwitchClipResponseData = gson.fromJson(jsonResponse, GetTwitchClipResponseData.class);
        getTwitchClipResponseData.getData().forEach(t->System.out.println(t.getUrl()));

        //download twitch clip
        //with selenium
        try {
            String path = "";
            path = twitchClipDownloadService.downloadPath(twitchClipDownloadService.getGameName());
            twitchClipDownloadService.downloadTwitchClips(twitchClipDownloadService.orderTwitchClips(getTwitchClipResponseData.getData()), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //compile video
        //save clips locally

        //upload video
    }
}

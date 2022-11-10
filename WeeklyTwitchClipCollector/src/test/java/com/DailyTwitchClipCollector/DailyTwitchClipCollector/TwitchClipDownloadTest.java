package com.DailyTwitchClipCollector.DailyTwitchClipCollector;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.GetTwitchClipResponseData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.TwitchClipResponseData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.TwitchClipVideoDataWrapper;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Services.TwitchClipDownloadService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {DailyTwitchClipCollectorApplication.class})
@ExtendWith(SpringExtension.class)
public class TwitchClipDownloadTest {
    @Autowired
    private TwitchClipDownloadService twitchClipDownloadService;

    Gson gson = new Gson();

    List<TwitchClipResponseData> getListOfTwitchClips() {
        String jsonResponse = twitchClipDownloadService.getDailyTwitchClip();
        GetTwitchClipResponseData getTwitchClipResponseData = gson.fromJson(jsonResponse, GetTwitchClipResponseData.class);
        return getTwitchClipResponseData.getData();
    }

    @Test
    void downloadWithPathTest(){
        try {
            twitchClipDownloadService.downloadTwitchClips(twitchClipDownloadService.orderTwitchClips(getListOfTwitchClips()), twitchClipDownloadService.downloadPath(twitchClipDownloadService.getGameName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void downloadFromURLTest() throws IOException {
        String path = "C:\\Users\\ECCOM\\Desktop\\TestDownloadFolder\\2022-10-30_2022-11-06_COUNTER_STRIKE_GLOBAL_OFFENSIVE";
        String url = "";
        twitchClipDownloadService.downloadFromURL(url, path);
    }

    @Test
    void getDownloadURL(){
        TwitchClipResponseData twitchClipResponseData = getListOfTwitchClips().get(0);
        String downloadURL = twitchClipDownloadService.getTwitchClipDownloadURL(new TwitchClipVideoDataWrapper(twitchClipResponseData,0));
        System.out.println("this is the url: " + downloadURL);
        assertFalse(downloadURL.equals(""));
    }

    @Test
    void downloadTwitchClipTest() throws IOException {
        String path = "";
        path = "C:\\Users\\ECCOM\\Desktop\\TestDownloadFolder\\2022-10-30_2022-11-06_COUNTER_STRIKE_GLOBAL_OFFENSIVE";
        TwitchClipResponseData twitchClipResponseData = getListOfTwitchClips().get(0);
        createDirectory(path);
//        this is where error happens
        twitchClipDownloadService.downloadTwitchClip(new TwitchClipVideoDataWrapper(twitchClipResponseData, 0),path);
    }

    void createDirectory(String path) throws IOException {
        Files.createDirectories(Paths.get(path));
    }
}

package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Services;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.TwitchClipResponseData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.TwitchClipVideoDataWrapper;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.VideoData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util.Dict;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TwitchClipDownloadService {
    //arrange the twitch clips such that
    //1) total length of video is less than 30 min
    //2) clips are ordered such that most viewed clips and least viewed clips are mixed together
    public VideoData orderTwitchClips(List<TwitchClipResponseData> twitchClipResponseDataList){
        double currentLength = 0.0;
        VideoData videoData = new VideoData();
        List<TwitchClipResponseData> reducedTwitchClips = new ArrayList<>();
        for(TwitchClipResponseData t: twitchClipResponseDataList){
            if(t.getDuration()<=30){
                reducedTwitchClips.add(t);
            }
            if(currentLength>= Dict.VIDEO_LENGTH){
                break;
            }
        }
        Object[] twitchClipsArray = reducedTwitchClips.toArray();
        for(int i = 0; i<reducedTwitchClips.size()/2; i++){
            videoData.add((TwitchClipResponseData) twitchClipsArray[i]);
            videoData.add((TwitchClipResponseData) twitchClipsArray[twitchClipsArray.length-i-1]);
        }
        if(reducedTwitchClips.size()%2==1){
            videoData.add((TwitchClipResponseData) twitchClipsArray[reducedTwitchClips.size()/2+1]);
        }
        return videoData;
    }


    //download all the twitch clip from TwitchClipResponseData
    public void downloadTwitchClips(VideoData videoData, String downloadPath){
        videoData.getTwitchClipVideoDataWrappers().forEach(t->downloadTwitchClip(t, downloadPath));
    }

    //return the path where the clips are to be saved in
    //fill in the path
    //return path base on game name and date
    public String downloadPath(String gameName) throws IOException {
        String to = ZonedDateTime.now( ZoneOffset.UTC ).minusDays(1).format( DateTimeFormatter.ISO_LOCAL_DATE);
        String from = ZonedDateTime.now( ZoneOffset.UTC ).minusDays(8).format( DateTimeFormatter.ISO_LOCAL_DATE);
        String fileNamePrefix = from+"_"+to+"_"+gameName;
        String path = Dict.FOLDER_LOCATION+"\\"+fileNamePrefix + "\\";
        Files.createDirectories(Paths.get(path));
        return path;
    }

    public void downloadTwitchClip(TwitchClipVideoDataWrapper t, String downloadPath){
        String downloadURL = getTwitchClipDownloadURL(t);
        try {
            downloadFromURL(downloadURL, downloadPath+"\\"+ generateMP4FileName(t));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateMP4FileName(TwitchClipVideoDataWrapper twitchClipVideoDataWrapper){
        TwitchClipResponseData t = twitchClipVideoDataWrapper.getTwitchClipResponseData();
        return twitchClipVideoDataWrapper.getOrder()+"_"+t.getBroadcaster_name()+"_"+t.getCreated_at().substring(0,t.getCreated_at().indexOf("T"))+Dict.MP4_SUFFIX;
    }

    //return twitch download url
    public String getTwitchClipDownloadURL(TwitchClipVideoDataWrapper twitchClipResponseData){
        String url = twitchClipResponseData.getTwitchClipResponseData().getUrl();
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://clipsey.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("clip-url-input")));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("get-download-link-button")));

        WebElement urlBox = driver.findElement(By.className("clip-url-input"));
        WebElement downloadButton = driver.findElement(By.className("get-download-link-button"));

        urlBox.sendKeys(url);
        downloadButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("download-clip-link")));

        WebElement downLoadLink = driver.findElement(By.className("download-clip-link"));
        String downloadURL = downLoadLink.getAttribute("href");

        driver.quit();

        return downloadURL;
    }

    public void downloadFromURL(String url, String downloadPath) throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(downloadPath);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }

    //sends http request to twitch api
    //retrieve twitch clip info in json String
    public String getDailyTwitchClip(){
        URL url = null;
        String gameID = getGameID();
        String to = ZonedDateTime.now( ZoneOffset.UTC ).minusDays(1).format( DateTimeFormatter.ISO_LOCAL_DATE) + "T00:00:00Z";
        String from = ZonedDateTime.now( ZoneOffset.UTC ).minusDays(8).format( DateTimeFormatter.ISO_LOCAL_DATE) + "T00:00:00Z";

        try {
            url = new URL(urlStringBuilder(gameID, from, to));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        http.setRequestProperty("Authorization", Dict.APP_ACCESS_TOKEN);
        http.setRequestProperty("Client-Id", Dict.CLIENT_ID);

        BufferedReader br = null;
        try {
            if (100 <= http.getResponseCode() && http.getResponseCode() <= 399) {
                br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String output = "";
        while (true) {
            try {
                output = br.readLine();
                if (output == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(output);
        }
        http.disconnect();
        return sb.toString();
    }

    private String getGameID(){
        DayOfWeek date = ZonedDateTime.now( ZoneOffset.UTC ).getDayOfWeek();
        switch (date){
            case MONDAY -> {
                return Dict.COUNTER_STRIKE_GLOBAL_OFFENSIVE;
            }
            case TUESDAY -> {
                return Dict.APEX_LEGENDS;
            }
            case WEDNESDAY -> {
                return Dict.DOTA_2;
            }
            case THURSDAY -> {
                return Dict.MINECRAFT;
            }
            case FRIDAY -> {
                return Dict.LEAGUE_OF_LEGENDS;
            }
            case SATURDAY -> {
                return Dict.VALORANT;
            }
            case SUNDAY -> {
                return Dict.GRAND_THEFT_AUTO_V;
            }
        }
        //never
        return Dict.WORLD_OF_WARCRAFT;
    }

    public String getGameName(){
        DayOfWeek date = ZonedDateTime.now( ZoneOffset.UTC ).getDayOfWeek();
        switch (date){
            case MONDAY -> {
                return "COUNTER_STRIKE_GLOBAL_OFFENSIVE";
            }
            case TUESDAY -> {
                return "APEX_LEGENDS";
            }
            case WEDNESDAY -> {
                return "DOTA_2";
            }
            case THURSDAY -> {
                return "MINECRAFT";
            }
            case FRIDAY -> {
                return "LEAGUE_OF_LEGENDS";
            }
            case SATURDAY -> {
                return "VALORANT";
            }
            case SUNDAY -> {
                return "GRAND_THEFT_AUTO_V";
            }
        }
        //never
        return "WORLD_OF_WARCRAFT";
    }

    private String urlStringBuilder(String gameID, String from, String to){
        return Dict.TWITCH_CLIP_URL+Dict.GAME_ID_PREFIX+gameID+Dict.FROM_DATE_PREFIX+from + Dict.TO_DATE_PREFIX + to + Dict.NUMBER_OF_CLIPS_PREFIX + Dict.MAXIMUN_NUMBER_OF_CLIPS;
    }
}

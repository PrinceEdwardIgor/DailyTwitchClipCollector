package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Job;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.TwitchClipResponseData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util.Dict;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DailyJob implements Job {
    Gson gson = new Gson();
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        String jsonResponse = getDailyTwitchClip();
        String jsonResponseWithoutPagination = (jsonResponse.substring(jsonResponse.indexOf('['), jsonResponse.indexOf(']')+1));
        Type listType = new TypeToken<List<TwitchClipResponseData>>(){}.getType();
        List<TwitchClipResponseData> listOfTwitchClip = gson.fromJson(jsonResponseWithoutPagination, listType);
        //TODO
        //retrieve list of url from twitch
        //download twitch clip
        //compile video
        //save clips locally
    }

    //sends http request to twitch api
    //retrieve twitch clip info in json String
    private String getDailyTwitchClip(){
        URL url = null;
        //TODO
        //gameID, from, to base on local time
        String gameID = "33214";
        String from = "2022-10-02T00:00:00Z";
        String to = "2022-10-09T00:00:00Z";
        try {
            url = new URL(urlStringBuilder(gameID, from, to));
//            url = new URL("https://api.twitch.tv/helix/clips?game_id=33214&started_at=2022-10-02T00:00:00Z&ended_at=2022-10-09T00:00:00Z");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        String output;
        while (true) {
            try {
                if (!((output = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(output);
        }
        http.disconnect();
        return sb.toString();
    }

    private String urlStringBuilder(String gameID, String from, String to){
        return Dict.TWITCH_CLIP_URL+Dict.GAME_ID_PREFIX+gameID+Dict.FROM_DATE_PREFIX+from + Dict.TO_DATE_PREFIX + to;
    }

}

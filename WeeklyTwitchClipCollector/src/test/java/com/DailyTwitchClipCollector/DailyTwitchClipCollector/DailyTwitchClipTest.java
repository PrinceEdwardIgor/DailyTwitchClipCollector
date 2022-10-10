package com.DailyTwitchClipCollector.DailyTwitchClipCollector;

import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data.TwitchClipResponseData;
import com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util.Dict;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@SpringBootTest
public class DailyTwitchClipTest {

    Gson gson = new Gson();
    @Test
    public void twitchClipTest(){
        String jsonResponse = getDailyTwitchClip();
        String jsonResponseWithoutPagination = (jsonResponse.substring(jsonResponse.indexOf('['), jsonResponse.indexOf(']')+1));
        Type listType = new TypeToken<List<TwitchClipResponseData>>(){}.getType();
        List<TwitchClipResponseData> listOfTwitchClip = gson.fromJson(jsonResponseWithoutPagination, listType);
        listOfTwitchClip.forEach(t->System.out.println("this is the url" + t.getUrl()));
    }

    private String getDailyTwitchClip(){
        URL url = null;
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
        http.setRequestProperty("Authorization", "Bearer 1fihkpjw2c64qlcbpmmbs2hza6zeck");
        http.setRequestProperty("Client-Id", "seqd5t2easki7jk8qenl8br5trizwm");

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

package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TwitchClipResponseData {
    private String clipID;
    private String url;
    private String embed_url;
    private String broadcaster_id;
    private String broadcaster_name;
    private String creator_id;
    private String creator_name;
    private String video_id;
    private String game_id;
    private String language;
    private String title;
    private int view_count;
    private String created_at;
    private String thumbnail_url;
    private double duration;
    private int vod_offset;
}

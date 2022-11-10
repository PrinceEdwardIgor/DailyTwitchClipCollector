package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TwitchClipVideoDataWrapper {
    TwitchClipResponseData twitchClipResponseData;

    //order of the twitch clip in the video
    int order;
}

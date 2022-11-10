package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoData {
    List<TwitchClipVideoDataWrapper> twitchClipVideoDataWrappers = new ArrayList<>();

    int currentNumberOfClips = 0;

    public void add(TwitchClipResponseData t){
        this.twitchClipVideoDataWrappers.add(new TwitchClipVideoDataWrapper(t, ++this.currentNumberOfClips));
    }
}

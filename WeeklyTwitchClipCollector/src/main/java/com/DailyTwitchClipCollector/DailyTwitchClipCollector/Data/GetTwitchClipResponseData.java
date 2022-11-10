package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTwitchClipResponseData {
    List<TwitchClipResponseData> data;
    Pagination pagination;
}

package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util;

public final class Dict {
    private Dict(){}

    public static final String DAILY_CRON = "0 0 * * *";
    public static final String TEST_CRON = "0 * * * * ?";
    public static final String TWITCH_CLIP_URL = "https://api.twitch.tv/helix/clips?";
    public static final String GAME_ID_PREFIX = "game_id=";
    public static final String FROM_DATE_PREFIX = "&started_at=";
    public static final String TO_DATE_PREFIX = "&ended_at=";
    public static final String CLIENT_ID = "seqd5t2easki7jk8qenl8br5trizwm";
    public static final String APP_ACCESS_TOKEN = "Bearer 1fihkpjw2c64qlcbpmmbs2hza6zeck";
}

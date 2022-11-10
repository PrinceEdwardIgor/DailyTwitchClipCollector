package com.DailyTwitchClipCollector.DailyTwitchClipCollector.Util;

public final class Dict {
    private Dict(){}

    public static final String DAILY_CRON = "0 0 8 * * ?";
    public static final String TEST_CRON = "0 10 17 * * ?";
    public static final String LEAGUE_OF_LEGENDS = "21779";
    public static final String VALORANT = "516575";
    public static final String GRAND_THEFT_AUTO_V = "32982";
    public static final String COUNTER_STRIKE_GLOBAL_OFFENSIVE = "32399";
    public static final String MINECRAFT = "27471";
    public static final String DOTA_2 = "29595";
    public static final String WORLD_OF_WARCRAFT = "18122";
    public static final String APEX_LEGENDS = "511224";
    public static final String TWITCH_CLIP_URL = "https://api.twitch.tv/helix/clips?";
    public static final String GAME_ID_PREFIX = "game_id=";
    public static final String FROM_DATE_PREFIX = "&started_at=";
    public static final String TO_DATE_PREFIX = "&ended_at=";
    public static final String NUMBER_OF_CLIPS_PREFIX = "&first=";
    public static final String MAXIMUN_NUMBER_OF_CLIPS = "100";

    //TODO
    public static final String FOLDER_LOCATION = "C:\\Users\\ECCOM\\Desktop\\TestDownloadFolder";
    public static final String CLIENT_ID = "seqd5t2easki7jk8qenl8br5trizwm";
    public static final String APP_ACCESS_TOKEN = "Bearer 1fihkpjw2c64qlcbpmmbs2hza6zeck";
    public static final double VIDEO_LENGTH = 1500;

    public static final String MP4_SUFFIX = ".mp4";
}

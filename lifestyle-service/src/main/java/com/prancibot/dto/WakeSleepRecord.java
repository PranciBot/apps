package com.prancibot.dto;

import com.prancibot.enums.Activity;

public class WakeSleepRecord {
    public String timezone;
    public String time;
    public String activity;

    public WakeSleepRecord(String timezone, String time, Activity activity) {
        this.timezone = timezone;
        this.time = time;
        this.activity = activity.getValue();
    }
}

package com.prancibot.lifestyle.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Activity {
    WAKE("wake"),
    SLEEP("sleep");

    private static final Map<String, Activity> MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(
                            a -> a.value.toLowerCase(),
                            a -> a
                    ));

    private final String value;

    Activity(String value) {
        this.value = value;
    }

    public static Activity from(String value) {
        Activity activity = MAP.get(value.toLowerCase());

        if (activity == null) {
            throw new IllegalArgumentException("Invalid activity: " + value);
        }

        return activity;
    }

    public String getValue() {
        return value;
    }
}

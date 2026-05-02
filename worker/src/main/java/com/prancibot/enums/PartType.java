package com.prancibot.enums;

import lombok.Getter;

@Getter
public enum PartType {
    Seconds(0),
    Minutes(1),
    Hour(2),
    DayOfMonth(3),
    Month(4),
    DayOfWeek(5);

    private final int index;

    PartType(int index) {
        this.index = index;
    }
}

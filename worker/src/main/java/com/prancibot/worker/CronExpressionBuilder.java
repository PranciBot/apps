package com.prancibot.worker;

import com.prancibot.worker.enums.PartType;

import java.util.Arrays;

public class CronExpressionBuilder {

    public static final String DEFAULT = "*";
    public static final String NO_SPEC = "?";

    private final String[] expressionSegments;

    public CronExpressionBuilder() {
        expressionSegments = new String[6];
        Arrays.fill(expressionSegments, DEFAULT);
    }

    public CronExpressionBuilder every(int value, PartType type) {
        int index = type.getIndex();
        mustEmptyBeforeAssign(index, type);
        expressionSegments[index] = "*/%s".formatted(value);
        return this;
    }

    public CronExpressionBuilder at(int value, PartType type) {
        int index = type.getIndex(); // FIXED (no ordinal)
        mustEmptyBeforeAssign(index, type);
        expressionSegments[index] = "%s".formatted(value);
        return this;
    }

    public CronExpression build() {
        handleQuartzDayConflict();

        StringBuilder res = new StringBuilder();
        for (String segment : expressionSegments) {
            res.append(segment).append(" ");
        }
        res.setLength(res.length() - 1);

        return new CronExpression(res.toString());
    }

    private void mustEmptyBeforeAssign(int index, PartType type) {
        if (!expressionSegments[index].equals(DEFAULT)) {
            throw new IllegalArgumentException(
                    "Cron expression at %s already assigned".formatted(type.name())
            );
        }
    }

    private void handleQuartzDayConflict() {
        int dom = PartType.DayOfMonth.getIndex();
        int dow = PartType.DayOfWeek.getIndex();

        boolean domSet = !expressionSegments[dom].equals(DEFAULT);
        boolean dowSet = !expressionSegments[dow].equals(DEFAULT);

        if (domSet && dowSet) {
            throw new IllegalArgumentException(
                    "Quartz does not allow both DayOfMonth and DayOfWeek to be set"
            );
        }

        if (!domSet && !dowSet) {
            expressionSegments[dow] = NO_SPEC;
        } else if (domSet) {
            expressionSegments[dow] = NO_SPEC;
        } else {
            expressionSegments[dom] = NO_SPEC;
        }
    }
}

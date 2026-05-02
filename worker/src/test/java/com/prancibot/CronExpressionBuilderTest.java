package com.prancibot;

import com.prancibot.enums.PartType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronExpressionBuilderTest {

    private CronExpressionBuilder builder;

    @BeforeEach
    public void setup() {
        builder = new CronExpressionBuilder();
    }

    @Test
    public void testRunEveryMinutes() {
        CronExpression actual = builder
                .every(5, PartType.Minutes)
                .build();

        assertEquals("* */5 * * * ?", actual.toString());
    }

    @Test
    public void testAt2_30EveryDay() {
        CronExpression actual = builder
                .at(0, PartType.Seconds)
                .at(30, PartType.Minutes)
                .at(2, PartType.Hour)
                .build();

        assertEquals("0 30 2 * * ?", actual.toString());
    }

    @Test
    public void testDefaultAllStars() {
        CronExpression actual = builder.build();

        assertEquals("* * * * * ?", actual.toString());
    }

    @Test
    public void testSpecificMonth() {
        CronExpression actual = builder
                .at(12, PartType.Month)
                .build();

        assertEquals("* * * * 12 ?", actual.toString());
    }

    @Test
    public void testSpecificDayOfWeek() {
        CronExpression actual = builder
                .at(1, PartType.DayOfWeek)
                .build();

        assertEquals("* * * ? * 1", actual.toString());
    }

    @Test
    public void testFullSpecificDateTime_shouldFail() {
        // Quartz does NOT allow both DayOfMonth and DayOfWeek
        assertThrows(IllegalArgumentException.class, () ->
                builder
                        .at(0, PartType.Seconds)
                        .at(10, PartType.Hour)
                        .at(1, PartType.DayOfMonth)
                        .at(1, PartType.Month)
                        .at(1, PartType.DayOfWeek)
                        .build()
        );
    }

    @Test
    public void testEveryHour() {
        CronExpression actual = builder
                .every(2, PartType.Hour)
                .build();

        assertEquals("* * */2 * * ?", actual.toString());
    }

    @Test
    public void testEveryDayOfMonth() {
        CronExpression actual = builder
                .every(3, PartType.DayOfMonth)
                .build();

        assertEquals("* * * */3 * ?", actual.toString());
    }

    @Test
    public void testEvery5MinutesAtSpecificHour() {
        CronExpression actual = builder
                .every(5, PartType.Minutes)
                .at(2, PartType.Hour)
                .build();

        assertEquals("* */5 2 * * ?", actual.toString());
    }

    @Test
    public void testOverrideSameField() {
        assertThrows(IllegalArgumentException.class, () ->
                builder
                        .at(10, PartType.Hour)
                        .at(2, PartType.Hour)
        );
    }

    @Test
    public void testMinuteBoundary() {
        CronExpression actual = builder
                .at(0, PartType.Minutes)
                .build();

        assertEquals("* 0 * * * ?", actual.toString());
    }

    @Test
    public void testMaxHourBoundary() {
        CronExpression actual = builder
                .at(23, PartType.Hour)
                .build();

        assertEquals("* * 23 * * ?", actual.toString());
    }

    @Test
    public void testEveryMinuteAndHour() {
        CronExpression actual = builder
                .every(5, PartType.Minutes)
                .every(2, PartType.Hour)
                .build();

        assertEquals("* */5 */2 * * ?", actual.toString());
    }
}
package com.prancibot.worker;

import lombok.Getter;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

@Getter
public class BaseWorker {
    private final String name;
    private final CronExpression expression;
    private final JobDetail detail;
    private final Trigger trigger;
    private final Class<? extends Job> job;

    public BaseWorker(String name, CronExpression expression, Class<? extends Job> job) {
        this.expression = expression;
        this.name = name;
        this.job = job;

        detail = JobBuilder.newJob(job)
                .withIdentity(name)
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(expression.toString()))
                .build();
    }
}

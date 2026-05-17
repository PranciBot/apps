package com.prancibot.worker;

import lombok.Getter;
import lombok.Setter;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkerScheduler {
    private final Scheduler scheduler;
    private final List<BaseWorker> workerList;
    private final List<JobListener> listeners;

    public WorkerScheduler() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        workerList = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void assignWorker(BaseWorker worker) throws SchedulerException {
        workerList.add(worker);
        scheduler.scheduleJob(worker.getDetail(), worker.getTrigger());
    }

    public void assignListener(JobListener listener) throws SchedulerException {
        listeners.add(listener);
        scheduler.getListenerManager().addJobListener(listener);
    }
}

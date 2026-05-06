package com.prancibot;

import com.prancibot.worker.BaseWorker;
import com.prancibot.worker.CronExpression;
import com.prancibot.worker.WorkerScheduler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkerSchedulerTest {

    private WorkerScheduler scheduler;

    @BeforeEach
    public void setup() throws SchedulerException {
        scheduler = new WorkerScheduler();
    }

    @AfterEach
    public void tearDown() throws SchedulerException {
        if (scheduler != null && scheduler.getScheduler() != null) {
            scheduler.getScheduler().shutdown();
        }
    }

    @Test
    public void testSchedulerCreation() {
        assertNotNull(scheduler);
        assertNotNull(scheduler.getScheduler());
        assertNotNull(scheduler.getWorkerList());
        assertNotNull(scheduler.getListeners());
    }

    @Test
    public void testSchedulerStarted() throws SchedulerException {
        assertTrue(scheduler.getScheduler().isStarted());
    }

    @Test
    public void testAssignWorker() throws SchedulerException {
        CronExpression expression = new CronExpression("0 0 12 * * ?");
        BaseWorker worker = new BaseWorker("TestWorker", expression, TestJob.class);

        scheduler.assignWorker(worker);

        assertEquals(1, scheduler.getWorkerList().size());
        assertEquals("TestWorker", scheduler.getWorkerList().get(0).getName());
    }

    @Test
    public void testAssignMultipleWorkers() throws SchedulerException {
        CronExpression expression1 = new CronExpression("0 0 12 * * ?");
        BaseWorker worker1 = new BaseWorker("Worker1", expression1, TestJob.class);

        CronExpression expression2 = new CronExpression("0 0 18 * * ?");
        BaseWorker worker2 = new BaseWorker("Worker2", expression2, TestJob.class);

        scheduler.assignWorker(worker1);
        scheduler.assignWorker(worker2);

        assertEquals(2, scheduler.getWorkerList().size());
    }

    @Test
    public void testAssignListener() throws SchedulerException {
        JobListener listener = new TestJobListener();

        scheduler.assignListener(listener);

        assertEquals(1, scheduler.getListeners().size());
        assertEquals("TestListener", scheduler.getListeners().get(0).getName());
    }

    @Test
    public void testAssignMultipleListeners() throws SchedulerException {
        JobListener listener1 = new TestJobListener();
        JobListener listener2 = new TestJobListener();

        scheduler.assignListener(listener1);
        scheduler.assignListener(listener2);

        assertEquals(2, scheduler.getListeners().size());
    }

    @Test
    public void testWorkerListInitiallyEmpty() {
        assertEquals(0, scheduler.getWorkerList().size());
    }

    @Test
    public void testListenersInitiallyEmpty() {
        assertEquals(0, scheduler.getListeners().size());
    }

    public static class TestJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // Test job implementation
        }
    }

    public static class TestJobListener implements JobListener {
        @Override
        public String getName() {
            return "TestListener";
        }

        @Override
        public void jobToBeExecuted(JobExecutionContext context) {
        }

        @Override
        public void jobExecutionVetoed(JobExecutionContext context) {
        }

        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        }
    }
}

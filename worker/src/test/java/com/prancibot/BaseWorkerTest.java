package com.prancibot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BaseWorkerTest {

    private CronExpression expression;
    private String workerName;

    @BeforeEach
    public void setup() {
        expression = new CronExpression("0 0 12 * * ?");
        workerName = "TestWorker";
    }

    @Test
    public void testBaseWorkerCreation() {
        BaseWorker worker = new BaseWorker(workerName, expression, TestJob.class);

        assertNotNull(worker);
        assertEquals(workerName, worker.getName());
        assertEquals(expression, worker.getExpression());
        assertEquals(TestJob.class, worker.getJob());
    }

    @Test
    public void testBaseWorkerHasJobDetail() {
        BaseWorker worker = new BaseWorker(workerName, expression, TestJob.class);

        assertNotNull(worker.getDetail());
        assertEquals(workerName, worker.getDetail().getKey().getName());
    }

    @Test
    public void testBaseWorkerHasTrigger() {
        BaseWorker worker = new BaseWorker(workerName, expression, TestJob.class);

        assertNotNull(worker.getTrigger());
    }

    @Test
    public void testBaseWorkerWithDifferentExpression() {
        CronExpression everyMinute = new CronExpression("* * * * * ?");
        BaseWorker worker = new BaseWorker("EveryMinuteWorker", everyMinute, TestJob.class);

        assertEquals("EveryMinuteWorker", worker.getName());
        assertEquals(everyMinute, worker.getExpression());
    }

    @Test
    public void testBaseWorkerWithDifferentJobClass() {
        class AnotherTestJob implements Job {
            @Override
            public void execute(JobExecutionContext context) throws JobExecutionException {
            }
        }

        BaseWorker worker = new BaseWorker(workerName, expression, AnotherTestJob.class);

        assertEquals(AnotherTestJob.class, worker.getJob());
    }

    @Test
    public void testMultipleWorkersWithSameName() {
        BaseWorker worker1 = new BaseWorker(workerName, expression, TestJob.class);
        BaseWorker worker2 = new BaseWorker(workerName, expression, TestJob.class);

        assertEquals(worker1.getName(), worker2.getName());
        // Note: They are different instances but share the same name
        assertNotNull(worker1.getDetail());
        assertNotNull(worker2.getDetail());
    }

    @Test
    public void testBaseWorkerJobDetailJobClass() {
        BaseWorker worker = new BaseWorker(workerName, expression, TestJob.class);

        assertEquals(TestJob.class, worker.getDetail().getJobClass());
    }

    public static class TestJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // Test job implementation
        }
    }
}

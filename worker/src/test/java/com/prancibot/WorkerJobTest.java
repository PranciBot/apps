package com.prancibot;

import com.prancibot.worker.WorkerJob;
import org.junit.jupiter.api.Test;
import org.quartz.JobExecutionContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class WorkerJobTest {

    @Test
    public void testWorkerJobCanBeInstantiated() {
        TestWorkerJob job = new TestWorkerJob();
        assertNotNull(job);
    }

    @Test
    public void testWorkerJobCanExecute() throws Exception {
        TestWorkerJob job = new TestWorkerJob();
        JobExecutionContext context = mock(JobExecutionContext.class);

        assertDoesNotThrow(() -> {
            job.execute(context);
        });
    }

    private static class TestWorkerJob extends WorkerJob {
        @Override
        public void execute(JobExecutionContext context) {
        }
    }
}

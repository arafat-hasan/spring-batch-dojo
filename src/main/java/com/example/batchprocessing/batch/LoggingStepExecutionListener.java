package com.example.batchprocessing.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;


@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingStepExecutionListener implements StepExecutionListener {
    @Override
    public void beforeStep(@SuppressWarnings("null") StepExecution stepExecution) {
        log.info("Step started at: " + stepExecution.getStartTime());
        // Add any setup or logic before the step starts
    }

    @Override
    public ExitStatus afterStep(@SuppressWarnings("null") StepExecution stepExecution) {
        log.info("Step finished at: " + stepExecution.getEndTime());
        // Add any cleanup or logic after the step completes
        return null; // Return null to use the default ExitStatus
    }
}

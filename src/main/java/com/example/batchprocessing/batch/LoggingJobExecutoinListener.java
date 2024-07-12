package com.example.batchprocessing.batch;

import org.springframework.batch.core.JobExecution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingJobExecutoinListener implements JobExecutionListener {

    @Override
    public void beforeJob(@SuppressWarnings("null") JobExecution jobExecution) {
      log.info("Job is about to start for Job ID: " + jobExecution.getJobId());
    }
   
    @Override
    public void afterJob(@SuppressWarnings("null") JobExecution jobExecution) {
        log.info("Job has completed with status: " + jobExecution.getStatus());
    }
}

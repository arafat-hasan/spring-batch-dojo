package com.example.learnspringbatch.config;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class CustomerJobExecutionListener implements JobExecutionListener {

    private static final ConcurrentHashMap<Long, Long> datasetLen = new ConcurrentHashMap<>();

    @SneakyThrows
    @Override
    public void beforeJob(JobExecution jobExecution) {
        String fileName = jobExecution.getJobParameters().getString("fileName");
        assert fileName != null;
        Resource resource = new ClassPathResource(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            Long lineCount = 0L;
            while (reader.readLine() != null) {
                lineCount++;
            }
            datasetLen.put(jobExecution.getJobId(), lineCount);
        }
    }

    public Long getDatasetLen(Long jobId) {
        if(datasetLen.containsKey(jobId)) {
            return datasetLen.get(jobId);
        } else {
            throw new RuntimeException("Invalid job id: " + jobId);
        }
    }

}

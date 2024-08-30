package com.example.learnspringbatch.controller;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobController {
    private RestTemplate restTemplate;
    private JobLauncher jobLauncher;
    private Job job;

    @PostMapping("/import")
    public void importCsvToDbJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();

        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/import-customers")
    public ResponseEntity<Long> importCsvToDBJob(@RequestBody String fileName) throws Exception {

        // check if the file exists
        Resource resource = new ClassPathResource(fileName);
        if(!resource.exists()) {
            throw new FileNotFoundException("File " + fileName + " Does Not Exist!");
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("fileName", fileName)
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            return ResponseEntity.ok(jobExecution.getId());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
            return ResponseEntity.ok(0L);
        }
    }

    @PostMapping("/stress-test")
    public void stressTest(@RequestBody String count) {
        String targetURL = "http://localhost:8000/jobs/import-customers";

        for(int i = 0; i < Integer.parseInt(count); i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            HttpEntity<String> request = new HttpEntity<>("customers-10k.csv", headers);

            restTemplate.postForObject(targetURL, request, String.class);
        }
    }
}

package com.example.batchprocessing;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchProcessingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));
	}

	@Autowired
	private JobLauncher launcher;

	@Autowired
	private Job importUserJob;

	@Override
	public void run(String... args) throws Exception {
		var map = new HashMap<String, JobParameter<?>>();
		map.put("file_name", new JobParameter<>("file.csv", String.class));
		map.put("timestamp", new JobParameter<>(System.currentTimeMillis(), Long.class));
		JobParameters parameters = new JobParameters(map);
		launcher.run(importUserJob, parameters);
	}
}

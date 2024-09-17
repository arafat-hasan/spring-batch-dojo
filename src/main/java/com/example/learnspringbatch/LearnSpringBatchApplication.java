package com.example.learnspringbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LearnSpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringBatchApplication.class, args);
	}
}

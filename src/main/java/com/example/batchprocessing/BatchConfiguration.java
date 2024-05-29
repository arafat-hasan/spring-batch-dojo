package com.example.batchprocessing;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {


    @Bean
    FlatFileItemReader<User> reader() {
		return new FlatFileItemReaderBuilder<User>()
			.name("userItemReader")
			.resource(new ClassPathResource("sample-data.csv"))
			.delimited()
			.names("firstName", "lastName")
			.targetType(User.class)
			.build();
	}

    @Bean
    UserItemProcessor processor() {
		return new UserItemProcessor();
	}

    @Bean
    JdbcBatchItemWriter<User> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<User>()
			.sql("INSERT INTO USERS (first_name, last_name) VALUES (:firstName, :lastName)")
			.dataSource(dataSource)
			.beanMapped()
			.build();
	}

    @Bean
    Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
		return new JobBuilder("importUserJob", jobRepository)
			.listener(listener)
			.start(step1)
			.build();
	}

    @Bean
    Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            FlatFileItemReader<User> reader, UserItemProcessor processor, JdbcBatchItemWriter<User> writer) {
		return new StepBuilder("step1", jobRepository)
			.<User, User> chunk(3, transactionManager)
			.reader(reader)
			.processor(processor)
			.writer(writer)
			.build();
	}
}

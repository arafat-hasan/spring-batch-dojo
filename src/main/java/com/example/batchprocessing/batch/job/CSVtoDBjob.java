package com.example.batchprocessing.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batchprocessing.batch.listener.LoggingJobExecutoinListener;
import com.example.batchprocessing.batch.listener.LoggingStepExecutionListener;
import com.example.batchprocessing.batch.processor.CustomerProcessor;
import com.example.batchprocessing.model.Customer;
import com.example.batchprocessing.repository.CustomerRepository;

@Configuration
public class CSVtoDBjob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;
    private final CustomerRepository customerRepository;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    public CSVtoDBjob(JobRepository jobRepository,
            PlatformTransactionManager txManager,
            CustomerRepository customerRepository,
            ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.jobRepository = jobRepository;
        this.txManager = txManager;
        this.customerRepository = customerRepository;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> reader(@Value("#{jobParameters['fileName']}") String fileName) {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource(fileName));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "phone", "address", "country", "dob");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                .<Customer, Customer>chunk(10, txManager)
                .listener(new LoggingStepExecutionListener())
                // .listener(new LoggingChunkListener())
                .reader(reader(null))
                .processor(processor())
                .writer(writer())
                .taskExecutor(threadPoolTaskExecutor)
                .build();
    }

    @Bean(name = "insertIntoDbFromCsvJob")
    public Job insertIntoDbFromCsvJob(Step step1) {
        return new JobBuilder("importCustomers", jobRepository)
                .start(step1)
                .listener(new LoggingJobExecutoinListener())
                .build();
    }
}

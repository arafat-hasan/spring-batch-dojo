package com.example.learnspringbatch.config;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChunkEventListener implements ChunkListener {

    private static final ConcurrentHashMap<Long, Long> processedChunks = new ConcurrentHashMap<>();

    @Override
    public void afterChunk(ChunkContext chunkContext) {

        StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
        JobExecution jobExecution = stepExecution.getJobExecution();
        Long jobExecutionId = jobExecution.getId();

        // Increment the count of processed chunks for the specific jobExecutionId
        processedChunks.merge(jobExecutionId, 1L, Long::sum);
    }

    public Long getProcessedChunkCount(Long jobExecutionId) {
        return processedChunks.get(jobExecutionId);
    }
}

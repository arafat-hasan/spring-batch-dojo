package com.example.batchprocessing.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(@SuppressWarnings("null") ChunkContext context) {
        log.info("Before chunk processing...");
        // Add any pre-processing logic here
    }
    @Override
    public void afterChunk(@SuppressWarnings("null") ChunkContext context) {
        log.info("After chunk processing...");
        // Add any post-processing logic here
    }
    @Override
    public void afterChunkError(@SuppressWarnings("null") ChunkContext context) {
        log.info("Error during chunk processing...");
        // Add error handling or cleanup logic here
    }
}

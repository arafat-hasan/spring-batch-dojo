package com.example.learnspringbatch.config;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class ChunkEventListener implements ChunkListener {

    private static final AtomicLong processedCount = new AtomicLong(0L);

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        processedCount.incrementAndGet();

//        System.err.println(Thread.currentThread().getName() + " processed " + processedCount.get() + " chunks");
    }

    public Long getProcessedCount() {
        return processedCount.get();
    }
}

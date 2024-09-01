package com.example.learnspringbatch.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ChunkService {
    @Value("${batch.job.chunk.size}")
    private Integer chunkSize;
}

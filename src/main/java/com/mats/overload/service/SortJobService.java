package com.mats.overload.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mats.overload.domain.SortJob;
import com.mats.overload.model.StatusEnum;
import com.mats.overload.repository.SortJobRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SortJobService {

    private final SortJobRepository repository;

    @Async
    public CompletableFuture<SortJob> processAsync(SortJob job) {
        long startedAt = System.currentTimeMillis();
        try {
            log.debug("Processing sort job started. payloadLength={}", job.getPayload().length());
            job.setStatus(StatusEnum.PROCESSING);
            repository.save(job);

            char[] chars = job.getPayload().toCharArray();
            Arrays.sort(chars);
            String result = new String(chars).trim().toLowerCase();
            job.setResult(result);
            Thread.sleep(result.length()*2);

            job.setStatus(StatusEnum.DONE);
            repository.save(job);
            log.info("Processing sort job completed. id={}, status={}, durationMs={}", job.getId(), job.getStatus(),
                    System.currentTimeMillis() - startedAt);
        } catch (InterruptedException ex) {
            job.setStatus(StatusEnum.FAILED);
            repository.save(job);
            log.error("Processing sort job interrupted. durationMs={}, error={}", System.currentTimeMillis() - startedAt,
                    ex.getMessage(), ex);
            Thread.currentThread().interrupt();
            throw new RuntimeException(ex);
        } catch (RuntimeException ex) {
            job.setStatus(StatusEnum.FAILED);
            repository.save(job);
            log.error("Processing sort job failed. durationMs={}, error={}", System.currentTimeMillis() - startedAt,
                    ex.getMessage(), ex);
            throw ex;
        }
        return CompletableFuture.completedFuture(job);
    }

    public SortJob save(SortJob job) {
        return repository.save(job);
    }

    public Optional<SortJob> get(UUID id) {
        return repository.findById(id);
    }
}

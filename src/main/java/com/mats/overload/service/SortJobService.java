package com.mats.overload.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mats.overload.domain.SortJob;
import com.mats.overload.model.StatusEnum;
import com.mats.overload.repository.SortJobRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SortJobService {

    @Autowired
    private SortJobRepository repository;

    public SortJob process(SortJob job) {
        long startedAt = System.currentTimeMillis();
        try {
            log.debug("Processing sort job started. payloadLength={}", job.getPayload().length());
            job.setStatus(StatusEnum.PROCESSING);
            char[] chars = job.getPayload().toCharArray();
            Arrays.sort(chars);
            String result = new String(chars);
            job.setResult(result.trim());
            repository.save(job);
            job.setStatus(StatusEnum.DONE);
            log.info("Processing sort job completed. id={}, status={}, durationMs={}", job.getId(), job.getStatus(),
                    System.currentTimeMillis() - startedAt);
            return job;
        } catch (RuntimeException ex) {
            log.error("Processing sort job failed. durationMs={}, error={}", System.currentTimeMillis() - startedAt,
                    ex.getMessage(), ex);
            throw ex;
        }
    }

    public Optional<SortJob> get(UUID id) {
        return repository.findById(id);
    }
}

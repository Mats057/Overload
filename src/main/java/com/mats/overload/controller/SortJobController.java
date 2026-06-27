package com.mats.overload.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mats.overload.domain.SortJob;
import com.mats.overload.model.StatusEnum;
import com.mats.overload.service.SortJobService;
import com.mats.overload.utils.SortJobMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sort")
public class SortJobController {

    private final SortJobService service;

    private final SortJobMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<SortJob> getById(@PathVariable String id) {
        log.info("Received get sort job request. id={}", id);
        return ResponseEntity.of(service.get(UUID.fromString(id)));
    }

    @PostMapping()
    public ResponseEntity<Void> publish(@RequestBody String payload) {
        log.info("Received publish sort job request. payloadLength={}", payload.length());
        SortJob job = mapper.payloadToSortJob(payload);
        job.setStatus(StatusEnum.PENDING);
        SortJob savedJob = service.save(job);
        service.processAsync(savedJob);
        log.debug("Publish sort job accepted. id={}, status={}", savedJob.getId(), savedJob.getStatus());
        return ResponseEntity.accepted().header("Location", "/sort/" + savedJob.getId()).build();
    }

}

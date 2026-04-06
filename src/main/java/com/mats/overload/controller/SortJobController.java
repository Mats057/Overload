package com.mats.overload.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mats.overload.domain.SortJob;
import com.mats.overload.service.SortJobService;
import com.mats.overload.utils.SortJobMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/sort")
public class SortJobController {

    @Autowired
    private SortJobService service;

    @Autowired
    private SortJobMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<SortJob> getById(@PathVariable String id) {
        log.info("Received get sort job request. id={}", id);
        return ResponseEntity.of(service.get(UUID.fromString(id)));
    }

    @PostMapping()
    public ResponseEntity<SortJob> publish(@RequestBody String payload) {
        log.info("Received publish sort job request. payloadLength={}", payload.length());
        SortJob job = mapper.payloadToSortJob(payload);
        SortJob processedJob = service.process(job);
        log.debug("Publish sort job completed. id={}, status={}", processedJob.getId(), processedJob.getStatus());
        return ResponseEntity.ok(processedJob);
    }

}

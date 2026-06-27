package com.mats.overload.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mats.overload.domain.SortJob;

public interface SortJobRepository extends JpaRepository<SortJob, UUID> {

}

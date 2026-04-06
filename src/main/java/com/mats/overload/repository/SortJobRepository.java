package com.mats.overload.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mats.overload.domain.SortJob;

@Repository
public interface SortJobRepository extends JpaRepository<SortJob, UUID> {

}

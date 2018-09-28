package com.ifrabbit.nk.quertz.repository;

import com.ifrabbit.nk.quertz.domain.JobConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobConfigRepository extends JpaRepository<JobConfig, Integer> {
    List<JobConfig> findAllByStatus(int status);
}

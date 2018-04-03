package com.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.center.domain.OperatingSystemLog;

public interface OperatingSystemLogRepository extends JpaRepository<OperatingSystemLog, Long> {

}
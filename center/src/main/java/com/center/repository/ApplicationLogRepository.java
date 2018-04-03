package com.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.center.domain.ApplicationLog;

public interface ApplicationLogRepository extends JpaRepository<ApplicationLog, Long> {

}
package com.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.center.domain.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
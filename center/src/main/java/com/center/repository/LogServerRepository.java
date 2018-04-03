package com.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.center.domain.LogServer;

public interface LogServerRepository extends JpaRepository<LogServer, Long> {

}
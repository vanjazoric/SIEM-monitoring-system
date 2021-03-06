package com.center.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.center.domain.Log;

public interface LogRepository extends MongoRepository<Log, String> {
    Collection<Log> findByAgentName(String agentName);
}
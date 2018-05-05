package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.ApplicationLog;

public interface ApplicationLogRepository extends MongoRepository<ApplicationLog, String> {

}
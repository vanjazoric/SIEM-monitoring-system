package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.LogServer;

public interface LogServerRepository extends MongoRepository<LogServer, String> {

}
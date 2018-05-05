package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.center.domain.OperatingSystemLog;

public interface OperatingSystemLogRepository extends MongoRepository<OperatingSystemLog, String> {

}
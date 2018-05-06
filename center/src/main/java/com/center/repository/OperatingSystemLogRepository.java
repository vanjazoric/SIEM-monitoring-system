package com.center.repository;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.center.domain.OperatingSystemLog;

public interface OperatingSystemLogRepository extends MongoRepository<OperatingSystemLog, String> {
	public ArrayList<OperatingSystemLog> findOperatingSystemByTimeStamp(Date timeStamp);
}
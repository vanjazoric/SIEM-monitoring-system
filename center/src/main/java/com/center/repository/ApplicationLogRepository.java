package com.center.repository;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.ApplicationLog;

public interface ApplicationLogRepository extends MongoRepository<ApplicationLog, String> {
	public ArrayList<ApplicationLog> findApplicationLogByApplication(String application);
	public ArrayList<ApplicationLog> findApplicationLogByPriority(String priority);
	public ArrayList<ApplicationLog> findApplicationLogByTimeStamp(Date timeStamp);

}
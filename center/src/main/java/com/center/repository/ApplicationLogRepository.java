package com.center.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.center.domain.ApplicationLog;

public interface ApplicationLogRepository extends
		MongoRepository<ApplicationLog, String> {

	@Query(value = "{ 'application' : ?0 }")
	public ArrayList<ApplicationLog> findApplicationLogByApplication(
			String application);

	@Query(value = "{'$or':[ {'priority':?0}, {'priority':?1}, {'priority':?2} ]}")
	public ArrayList<ApplicationLog> findApplicationLogByPriority(String p1,
			String p2, String p3);
	
	@Query(value = "{ 'message' : ?0 }")
	public ArrayList<ApplicationLog> findApplicationLogByMessage(
			String message);

	@Query(value = "{ '_class' : ?0 }")
	public ArrayList<ApplicationLog> findAllByClass(String className);
}
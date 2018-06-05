package com.center.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.center.domain.OperatingSystemLog;

public interface OperatingSystemLogRepository extends
		MongoRepository<OperatingSystemLog, String> {

	public ArrayList<OperatingSystemLog> findOperatingSystemLogByLevel(String method);

	public ArrayList<OperatingSystemLog> findOperatingSystemLogByEventId(int http);

	public ArrayList<OperatingSystemLog> findOperatingSystemLogBySource(String ip);
	
	@Query(value = "{'$or':[ {'level':?0}, {'level':?1}, {'level':?2} ]}")
	public ArrayList<OperatingSystemLog> findOperatingSystemLogByLevel(String p1,
			String p2, String p3);

	@Query(value = "{ '_class' : ?0 }")
	public ArrayList<OperatingSystemLog> findAllByClass(String className);
}
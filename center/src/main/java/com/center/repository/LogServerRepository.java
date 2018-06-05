package com.center.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.center.domain.LogServer;

public interface LogServerRepository extends MongoRepository<LogServer, String> {

	public ArrayList<LogServer> findLogServerByClientIp(String ip);

	@Query(value = "{'$or':[ {'method':?0}, {'method':?1}, {'method':?2} ]}")
	public ArrayList<LogServer> findLogServerByMethod(String p1, String p2,
			String p3);

	@Query(value = "{ '_class' : ?0 }")
	public ArrayList<LogServer> findAllByClass(String className);

	@Query(value = "{'$or':[ {'httpStatus':?0}, {'httpStatus':?1}, {'httpStatus':?2} ]}")
	public ArrayList<LogServer> findLogServerByHttpStatus(int p1, int p2,
			int p3);

}
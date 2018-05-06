package com.center.repository;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.LogServer;

public interface LogServerRepository extends MongoRepository<LogServer, String> {
	public ArrayList<LogServer> findLogServerByTimeStamp(Date timeStamp);
	public ArrayList<LogServer> findLogServerByMethod(String method);
	

}
package com.center.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "agents")
public class Agent {
	
	@Id
	private String id;
	
	@DBRef
	private Set<Log> logs;

	public Agent() {
		super();
	}

	public Agent(String id, Set<Log> logs) {
		super();
		this.id = id;
		this.logs = logs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}
	
	
	
}

package com.center.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "agents")
public class Agent {
	
	@Id
	private String id;
	private String name;
	
	//@DBRef
	private Set<Log> logs;

	public Agent() {
		super();
	}
	
	public Agent(String id, String name, Set<Log> logs) {
		super();
		this.id = id;
		this.name = name;
		this.logs = logs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}
	
	
	
}

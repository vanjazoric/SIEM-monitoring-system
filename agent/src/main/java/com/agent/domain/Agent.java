package com.agent.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;

public class Agent {
	
	private String id;
	private String name;
	
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

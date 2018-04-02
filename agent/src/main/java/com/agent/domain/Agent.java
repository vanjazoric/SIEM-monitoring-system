package com.agent.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class Agent {
	
	private Long id;
	
	private Set<Log> logs;

	public Agent() {
		super();
	}

	public Agent(Long id, Set<Log> logs) {
		super();
		this.id = id;
		this.logs = logs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}
	
	
	
}

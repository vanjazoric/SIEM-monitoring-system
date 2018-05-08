package com.agent.domain;

import java.util.Date;

public class Log {

	private Long id;

	//private String priority;

	private String version;

	private Date timeStamp;

	private String hostName;

	//private String application;

	private Long processId;

	//private Long MessageId;
    
	private Agent agent;

	public Log() {
		super();
	}

	public Log(Long id, Date timeStamp, Agent agent) {
		super();
		this.id = id;
        this.timeStamp=timeStamp;
		this.agent = agent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", timeStamp=" + timeStamp + ", agent=" + agent + "]";
	}



}
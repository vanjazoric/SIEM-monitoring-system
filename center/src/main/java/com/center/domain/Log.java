package com.center.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
public class Log {

	@Id
	private String id;

	//private String priority;

	private String version;

	private Date timeStamp;

	private String hostName;

	//private String application;

	private Long processId;

	//private Long MessageId;

	// @DBRef
	private String agentName;

	public Log() {
		super();
	}

	public Log(String id, Date timeStamp, String agentName) {
		super();
		this.id = id;
		this.timeStamp = timeStamp;
		this.agentName = agentName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", timeStamp=" + timeStamp + ", agentName="
				+ agentName + "]";
	}

}
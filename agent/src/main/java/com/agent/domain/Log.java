package com.agent.domain;

import java.util.Date;

public class Log {

	private Long id;

	private String priority;

	private String version;

	private Date timeStamp;

	private String hostName;

	private String application;

	private Long processId;

	private Long MessageId;

	private Agent agent;

	public Log() {
		super();
	}

	public Log(Long id, String priority, String version, Date timeStamp,
			String hostName, String application, Long processId,
			Long messageId, Agent agent) {
		super();
		this.id = id;
		this.priority = priority;
		this.version = version;
		this.timeStamp = timeStamp;
		this.hostName = hostName;
		this.application = application;
		this.processId = processId;
		MessageId = messageId;
		this.agent = agent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getMessageId() {
		return MessageId;
	}

	public void setMessageId(Long messageId) {
		MessageId = messageId;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", priority=" + priority + ", version="
				+ version + ", timeStamp=" + timeStamp + ", hostName="
				+ hostName + ", application=" + application + ", processId="
				+ processId + ", MessageId=" + MessageId + ", agent=" + agent
				+ "]";
	}

}

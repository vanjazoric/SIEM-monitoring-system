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

	//private String version;

	private Date timeStamp;

	//private String hostName;

	//private String application;

	//private Long processId;

	//private Long MessageId;

	//@DBRef
	private Agent agent;

	public Log() {
		super();
	}

	public Log(String id, Date timeStamp, Agent agent) {
		super();
		this.id = id;
        this.timeStamp=timeStamp;
		this.agent = agent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	@Override
	public String toString() {
		return "Log [id=" + id + ", timeStamp=" + timeStamp + ", agent=" + agent + "]";
	}



}

package com.center.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ApplicationLog extends Log{
	
	@Column
	private int eventId;
	
	@Column
	private String priority;
	
	@Column
	private String application;
	
	@Column
	private Long messageId;
	
	@Column
	private String message;

	public ApplicationLog() {
		super();
	}

	public ApplicationLog(Long id, Date timeStamp, Agent agent, int eventId, 
			String priority, String application, Long messageId, String message) {
		super(id, timeStamp, agent);
		this.eventId = eventId;
		this.priority = priority;
		this.application = application;
		this.messageId = messageId;
		this.message = message;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ApplicationLog [eventId=" + eventId + ", priority=" + priority + ", application=" + application
				+ ", messageId=" + messageId + ", message=" + message + "]";
	}
	
	
	
}

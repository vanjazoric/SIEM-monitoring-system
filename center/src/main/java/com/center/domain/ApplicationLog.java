package com.center.domain;

import java.util.Date;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logs")
@TypeAlias("log_app")
public class ApplicationLog extends Log{
	
	private int eventId;
	private String priority;
	private String application;
	private Long messageId;
	private String message;

	public ApplicationLog() {
		super();
	}

	public ApplicationLog(String id, Date timeStamp, String agentName, int eventId, 
			String priority, String application, Long messageId, String message) {
		super(id, timeStamp, agentName);
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
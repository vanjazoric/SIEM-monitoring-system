/**
 * 
 */
package com.center.domain;

import java.util.Date;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vanja
 *
 */

@Document(collection = "logs")
@TypeAlias("log_OS")
public class OperatingSystemLog extends Log {

	private String level;
	private int eventId;
	private String taskCategory;
	private String source;

	public OperatingSystemLog() {
		super();
	}

	public OperatingSystemLog(String id, String level, Date timeStamp,
			String source, int eventId, String agentName) {
		super(id, timeStamp, agentName);
		this.eventId = eventId;
		this.level = level;
		this.source = source;
	}
	
	public OperatingSystemLog(String id, String level, Date timeStamp,
			String source, int eventId, String taskCategory, String agentName) {
		super(id, timeStamp, agentName);
		this.eventId = eventId;
		this.level = level;
		this.source = source;
		this.taskCategory = taskCategory;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "OperatingSystemLog [level=" + level + ", eventId=" + eventId
				+ ", taskCategory=" + taskCategory + ", source=" + source + "]";
	}

}

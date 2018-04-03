/**
 * 
 */
package com.center.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author Vanja
 *
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class OperatingSystemLog extends Log {

	@Enumerated(EnumType.STRING)
	private Level level;
	
	@Column
	private int eventId;
	
	@Column
	private String taskCategory;
	
	@Column
	private String source;

	public OperatingSystemLog() {
		super();
	}

	public OperatingSystemLog(Long id, Level level, Date timeStamp,
			String source, int eventId, Agent agent) {
		super(id, timeStamp, agent);
		this.eventId = eventId;
		this.level = level;
		this.source = source;
	}
	
	public OperatingSystemLog(Long id, Level level, Date timeStamp,
			String source, int eventId, String taskCategory, Agent agent) {
		super(id, timeStamp, agent);
		this.eventId = eventId;
		this.level = level;
		this.source = source;
		this.taskCategory = taskCategory;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
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

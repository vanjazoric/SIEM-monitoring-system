package com.center.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alarms")
public class Alarm {
	@Id
	private String id;
	private String description;
	private int numOfItems;
	private int duration;
	private String conditions;
	private int cursor;
	private String type;
	private String agentName;
	private Date date;
	
	public Alarm() {
		super();
	}

	public Alarm(String description, int numOfItems, int duration, String conditions, Date date) {
		super();
		this.description = description;
		this.numOfItems = numOfItems;
		this.duration = duration;
		this.conditions = conditions;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(int numOfItems) {
		this.numOfItems = numOfItems;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	public int getCursor() {
		return cursor;
	}

	public void setCursor(int cursor) {
		this.cursor = cursor;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Alarm [id=" + id + ", description=" + description + ", numOfItems=" + numOfItems + ", duration="
				+ duration + ", conditions=" + conditions + ", cursor=" + cursor + ", type=" + type + ", agentName="
				+ agentName + ", date=" + date + "]";
	}
	
}
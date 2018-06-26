package com.center.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "alarms")
public class Alarm {
	@Id
	public String id;
	public String description;
	public int numOfItems;
	public int duration;
	public String conditions;
	
	public Alarm() {
		super();
	}

	public Alarm(String id, String description, int numOfItems, int duration, String conditions) {
		super();
		this.id = id;
		this.description = description;
		this.numOfItems = numOfItems;
		this.duration = duration;
		this.conditions = conditions;
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

	@Override
	public String toString() {
		return "Alarm [id=" + id + ", description=" + description + ", numOfItems=" + numOfItems + ", duration="
				+ duration + ", conditions=" + conditions + "]";
	}
	
}

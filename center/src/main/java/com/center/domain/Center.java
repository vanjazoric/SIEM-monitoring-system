package com.center.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "centers")
public class Center {
	
	@Id
	private String id;
	//@DBRef
	private Set<Agent> agents;

	public Center() {
		super();
	}

	public Center(String id, Set<Agent> agents) {
		super();
		this.id = id;
		this.agents = agents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Agent> getAgents() {
		return agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

	@Override
	public String toString() {
		return "Center [id=" + id + ", agents=" + agents + "]";
	}

}

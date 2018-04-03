package com.center.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Center {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable = false)
	private Long id;
	
	@OneToMany
	private Set<Agent> agents;

	public Center() {
		super();
	}

	public Center(Long id, Set<Agent> agents) {
		super();
		this.id = id;
		this.agents = agents;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

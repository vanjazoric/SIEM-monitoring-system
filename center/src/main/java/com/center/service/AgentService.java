package com.center.service;

import java.util.Collection;

import com.center.domain.Agent;

public interface AgentService {

	Collection<Agent> findAll();

	Agent findOne(Long id);

	Agent create(Agent agent) throws Exception;

	Agent update(Agent agent) throws Exception;

	void delete(Agent agent) throws Exception;

}
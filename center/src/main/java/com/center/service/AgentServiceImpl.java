package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.Agent;
import com.center.repository.AgentRepository;

@Service
public class AgentServiceImpl implements AgentService{
	
	@Autowired
	private AgentRepository agentRepository;

	@Override
	public Collection<Agent> findAll() {
		// TODO Auto-generated method stub
		return agentRepository.findAll();
	}

	@Override
	public Agent findOne(Long id) {
		// TODO Auto-generated method stub
		return agentRepository.findOne(id);
	}

	@Override
	public Agent create(Agent agent) throws Exception {
		// TODO Auto-generated method stub
		return agentRepository.save(agent);
		
	}

	@Override
	public Agent update(Agent agent) throws Exception {
		// TODO Auto-generated method stub
		return agentRepository.save(agent);
	}

	@Override
	public void delete(Agent agent) throws Exception {
		// TODO Auto-generated method stub
		agentRepository.delete(agent);
		
	}

	
}

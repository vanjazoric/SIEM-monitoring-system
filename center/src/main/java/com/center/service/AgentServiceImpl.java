package com.center.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.Agent;
import com.center.repository.AgentRepository;

@Service
public class AgentServiceImpl implements AgentService{

	@Autowired
    AgentRepository agentRepository;
	
	@Override
	public Agent saveAgentInfo(Agent agent){

        Agent ai = agentRepository.findAgentInfoByName(agent.getName());

        if (agentRepository.findAgentInfoByName(agent.getName()) != null)
            agent.setId(ai.getId());

        return agentRepository.save(agent);
    }
	
}

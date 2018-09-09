package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {

	Agent findAgentInfoByName(String name);
    Agent findAgentInfoById(String id);
    void deleteAgentInfoByName(String name);
}
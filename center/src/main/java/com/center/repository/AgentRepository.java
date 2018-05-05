package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {

}
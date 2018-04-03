package com.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.center.domain.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

}
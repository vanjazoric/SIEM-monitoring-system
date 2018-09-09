package com.center.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.center.DTO.AgentDTO;
import com.center.domain.Agent;
import com.center.repository.AgentRepository;
import com.center.service.AgentService;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {

	@Autowired
	AgentService agentService;
	
	@Autowired
	AgentRepository agentRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent)
    {
		Agent exists = agentRepository.findOne(agent.getId());
		
		if(exists != null){
			return new ResponseEntity<Agent>(HttpStatus.CONFLICT);
		}
        
		Agent saved = null;
		try {
			saved = agentRepository.insert(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<Agent>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(value = "/update", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Agent> updateAgent(@RequestBody Agent agent)
    {
		Agent exists = agentRepository.findOne(agent.getId());
		
		if(exists == null){
			return new ResponseEntity<Agent>(HttpStatus.NOT_FOUND);
		}
        
		Agent saved = null;
		try {
			saved = agentRepository.save(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<Agent>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Agent> getAgent(@PathVariable String id) {
		Agent agent = agentRepository.findOne(id);
		
		if(agent == null){
			return new ResponseEntity<Agent>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Agent>(agent,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< ArrayList<Agent> > getAgents() {
		ArrayList<Agent> agents = (ArrayList<Agent>) agentRepository.findAll();
		return new ResponseEntity< ArrayList<Agent> >(agents,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Agent> deleteAgentById(@PathVariable String id) {
		Agent agent = agentRepository.findOne(id);
		
		if(agent == null){
			return new ResponseEntity<Agent>(HttpStatus.NOT_FOUND);
		}

		try {
			agentRepository.delete(agent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Agent>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", 
	method = RequestMethod.DELETE,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Agent> deleteAgent(@RequestBody Agent agent)
    {
		Agent exists = agentRepository.findOne(agent.getId());
		
		if(exists == null){
			return new ResponseEntity<Agent>(HttpStatus.NOT_FOUND);
		}
        
		try {
			agentRepository.delete(agent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<Agent>(HttpStatus.OK);
    }
	
    @PostMapping(value = "/save", consumes = "application/json")
    public ResponseEntity<String> saveAgent(@RequestBody AgentDTO agentInfoDTO){
        logger.debug("Accessing POST /agent/save");
        agentService.saveAgentInfo(new Agent(agentInfoDTO));
        return new ResponseEntity<>("Agent accepted by center.", HttpStatus.OK);
    }
	
}

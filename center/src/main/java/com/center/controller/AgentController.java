package com.center.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

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
    
    @PostMapping(value = "/change-parent/{childName}/{parentName}", consumes = "application/json")
    public ResponseEntity<String> changeParent(@PathVariable String childName, @PathVariable String parentName){
        Agent childAgent = this.agentRepository.findAgentInfoByName(childName);
        Agent parentAgent = this.agentRepository.findAgentInfoByName(parentName);
        childAgent.setParentIp(parentAgent.getIp());
        childAgent.setParentName(parentAgent.getName());
        childAgent.setParentPort(parentAgent.getPort());
        agentRepository.save(childAgent);
        
        String sendTo = "https://" + childAgent.getIp() + ":"
				+ childAgent.getPort() + "/change-parent/" + childAgent.getParentIp().substring(0, 3)+ "/" + childAgent.getParentPort()  + "/" + childAgent.getParentIp();
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		try {
			sendToAgent(sendTo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Agent parent changed.", HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>("Agent parent changed.", HttpStatus.OK);
    }
	
	public void sendToAgent(String sendTo) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        //headers.set("agentName", this.agent.getName());
        headers.setContentType(MediaType.APPLICATION_JSON);
		//sendTo=sendTo+"/create";
		
		HttpEntity<String> request= new HttpEntity<>("", headers);
		
		/*ResponseEntity<ApplicationLog>*/Object response = restTemplate.exchange(sendTo,HttpMethod.GET, request,Object.class);
		System.out.println("Status code:"/* + result.getStatusCode()*/);
	}
    
    @PostMapping(value = "/change-fw-source/{agentName}/{newValue}", consumes = "application/json")
    public ResponseEntity<String> changeFwSource(@PathVariable String agentName, @PathVariable String newValue){
        Agent childAgent = this.agentRepository.findAgentInfoByName(agentName);
        childAgent.setFwLogsDest(newValue + ".txt");
        agentRepository.save(childAgent);
        
        String sendTo = "https://" + childAgent.getIp() + ":"
				+ childAgent.getPort() + "/change-fw-source/" + childAgent.getParentIp().substring(0, 3)+ "/" + childAgent.getParentPort()  + "/" + childAgent.getParentIp();
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		try {
			//sendToAgent(sendTo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Fw source changed.", HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>("Fw source changed.", HttpStatus.OK);
    }
    
    @PostMapping(value = "/change-app-source/{agentName}/{newValue}", consumes = "application/json")
    public ResponseEntity<String> changeAppSource(@PathVariable String agentName, @PathVariable String newValue){
        Agent childAgent = this.agentRepository.findAgentInfoByName(agentName);
        childAgent.setApLogsDest(newValue + ".txt");
        agentRepository.save(childAgent);
        
        String sendTo = "https://" + childAgent.getIp() + ":"
				+ childAgent.getPort() + "/change-app-source/" + childAgent.getParentIp().substring(0, 3)+ "/" + childAgent.getParentPort()  + "/" + childAgent.getParentIp();
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		try {
			//sendToAgent(sendTo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("App source changed.", HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>("App source changed.", HttpStatus.OK);
    }
    
    @PostMapping(value = "/change-server-source/{agentName}/{newValue}", consumes = "application/json")
    public ResponseEntity<String> changeServerSource(@PathVariable String agentName, @PathVariable String newValue){
        Agent childAgent = this.agentRepository.findAgentInfoByName(agentName);
        childAgent.setServerLogsDest(newValue + ".txt");
        agentRepository.save(childAgent);
        
        String sendTo = "https://" + childAgent.getIp() + ":"
				+ childAgent.getPort() + "/change-server-source/" + childAgent.getParentIp().substring(0, 3)+ "/" + childAgent.getParentPort()  + "/" + childAgent.getParentIp();
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		try {
			//sendToAgent(sendTo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("App source changed.", HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>("App source changed.", HttpStatus.OK);
    }
    
    @PostMapping(value = "/connect-to-center/{agentName}", consumes = "application/json")
    public ResponseEntity<String> changeServerSource(@PathVariable String agentName){
        Agent childAgent = this.agentRepository.findAgentInfoByName(agentName);
        childAgent.setParentName("center");
        childAgent.setParentIp("localhost");
        childAgent.setParentPort("8443/api");
        agentRepository.save(childAgent);
        
        String sendTo = "https://" + childAgent.getIp() + ":"
				+ childAgent.getPort() + "/connect-to-center/" + childAgent.getParentIp().substring(0, 3)+ "/" + childAgent.getParentPort()  + "/" + childAgent.getParentIp();
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		try {
			//sendToAgent(sendTo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("App source changed.", HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<>("App source changed.", HttpStatus.OK);
    }
}


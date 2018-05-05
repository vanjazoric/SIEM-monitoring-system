package com.center.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.LogFirewall;
import com.center.repository.LogFirewallRepository;

@RestController
@RequestMapping(value = "/logfirewall")
public class LogFirewallController {

	@Autowired
	LogFirewallRepository logFirewallRepository;
	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogFirewall> createLogFirewall(@RequestBody LogFirewall logfirewall)
    {
		LogFirewall exists = logFirewallRepository.findOne(logfirewall.getId());
		
		if(exists != null){
			return new ResponseEntity<LogFirewall>(HttpStatus.CONFLICT);
		}
        
		LogFirewall saved = null;
		try {
			saved = logFirewallRepository.insert(logfirewall);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<LogFirewall>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(value = "/update", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogFirewall> updateLogFirewall(@RequestBody LogFirewall logfirewall)
    {
		LogFirewall exists = logFirewallRepository.findOne(logfirewall.getId());
		
		if(exists == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}
        
		LogFirewall saved = null;
		try {
			saved = logFirewallRepository.save(logfirewall);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<LogFirewall>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> getLogFirewall(@PathVariable String id) {
		LogFirewall logfirewall = logFirewallRepository.findOne(id);
		
		if(logfirewall == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<LogFirewall>(logfirewall,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< ArrayList<LogFirewall> > getLogFirewalls() {
		ArrayList<LogFirewall> logfirewalls = (ArrayList<LogFirewall>) logFirewallRepository.findAll();
		return new ResponseEntity< ArrayList<LogFirewall> >(logfirewalls,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> deleteLogFirewallById(@PathVariable String id) {
		LogFirewall logfirewall = logFirewallRepository.findOne(id);
		
		if(logfirewall == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		try {
			logFirewallRepository.delete(logfirewall);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LogFirewall>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", 
	method = RequestMethod.DELETE,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogFirewall> deleteLogFirewall(@RequestBody LogFirewall logfirewall)
    {
		LogFirewall exists = logFirewallRepository.findOne(logfirewall.getId());
		
		if(exists == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}
        
		try {
			logFirewallRepository.delete(logfirewall);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<LogFirewall>(HttpStatus.OK);
    }
	
}

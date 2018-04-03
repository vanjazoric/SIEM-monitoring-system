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
import com.center.domain.OperatingSystemLog;
import com.center.service.LogFirewallService;

@RestController
@RequestMapping(value = "/logfirewall")
public class LogFirewallController {

	@Autowired
	LogFirewallService logfirewallService;
	
	@CrossOrigin
	@RequestMapping(value = "/createall", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> createFirewallLogs(
			@RequestBody ArrayList<LogFirewall> logs) throws Exception {
		for (LogFirewall log : logs) {
			logfirewallService.create(log);
		}
		return new ResponseEntity<ArrayList<LogFirewall>>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogFirewall> createLogFirewall(@RequestBody LogFirewall logfirewall)
    {
		LogFirewall exists = logfirewallService.findOne(logfirewall.getId());
		
		if(exists != null){
			return new ResponseEntity<LogFirewall>(HttpStatus.CONFLICT);
		}
        
		LogFirewall saved = null;
		try {
			saved = logfirewallService.create(logfirewall);
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
		LogFirewall exists = logfirewallService.findOne(logfirewall.getId());
		
		if(exists == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}
        
		LogFirewall saved = null;
		try {
			saved = logfirewallService.update(logfirewall);
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
		LogFirewall logfirewall = logfirewallService.findOne(Long.parseLong(id));
		
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
		ArrayList<LogFirewall> logfirewalls = (ArrayList<LogFirewall>) logfirewallService.findAll();
		return new ResponseEntity< ArrayList<LogFirewall> >(logfirewalls,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> deleteLogFirewallById(@PathVariable String id) {
		LogFirewall logfirewall = logfirewallService.findOne(Long.parseLong(id));
		
		if(logfirewall == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		try {
			logfirewallService.delete(logfirewall);
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
		LogFirewall exists = logfirewallService.findOne(logfirewall.getId());
		
		if(exists == null){
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}
        
		try {
			logfirewallService.delete(logfirewall);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<LogFirewall>(HttpStatus.OK);
    }
	
}

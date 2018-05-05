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

import com.center.domain.LogServer;
import com.center.repository.LogServerRepository;

@RestController
@RequestMapping(value = "/logserver")
public class LogServerController {

	@Autowired
	LogServerRepository logServerRepository;
	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogServer> createLogServer(@RequestBody LogServer logserver)
    {
		LogServer exists = logServerRepository.findOne(logserver.getId());
		
		if(exists != null){
			return new ResponseEntity<LogServer>(HttpStatus.CONFLICT);
		}
        
		LogServer saved = null;
		try {
			saved = logServerRepository.save(logserver);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<LogServer>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(value = "/update", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogServer> updateLogServer(@RequestBody LogServer logserver)
    {
		LogServer exists = logServerRepository.findOne(logserver.getId());
		
		if(exists == null){
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}
        
		LogServer saved = null;
		try {
			saved = logServerRepository.save(logserver);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<LogServer>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogServer> getLogServer(@PathVariable String id) {
		LogServer logserver = logServerRepository.findOne(id);
		
		if(logserver == null){
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<LogServer>(logserver,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< ArrayList<LogServer> > getLogServers() {
		ArrayList<LogServer> logservers = (ArrayList<LogServer>) logServerRepository.findAll();
		return new ResponseEntity< ArrayList<LogServer> >(logservers,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogServer> deleteLogServerById(@PathVariable String id) {
		LogServer logserver = logServerRepository.findOne(id);
		
		if(logserver == null){
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}

		try {
			logServerRepository.delete(logserver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LogServer>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", 
	method = RequestMethod.DELETE,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogServer> deleteLogServer(@RequestBody LogServer logserver)
    {
		LogServer exists = logServerRepository.findOne(logserver.getId());
		
		if(exists == null){
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}
        
		try {
			logServerRepository.delete(logserver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<LogServer>(HttpStatus.OK);
    }
	
}

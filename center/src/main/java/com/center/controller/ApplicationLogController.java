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

import com.center.domain.ApplicationLog;
import com.center.service.ApplicationLogService;

@RestController
@RequestMapping(value = "/applicationlog")
public class ApplicationLogController {

	@Autowired
	ApplicationLogService applicationlogService;

	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationLog> createApplicationLog(@RequestBody ApplicationLog applicationlog)
    {
		ApplicationLog exists = applicationlogService.findOne(applicationlog.getId());
		
		if(exists != null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.CONFLICT);
		}
        
		ApplicationLog saved = null;
		try {
			saved = applicationlogService.create(applicationlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<ApplicationLog>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(value = "/update", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationLog> updateApplicationLog(@RequestBody ApplicationLog applicationlog)
    {
		ApplicationLog exists = applicationlogService.findOne(applicationlog.getId());
		
		if(exists == null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
		}
        
		ApplicationLog saved = null;
		try {
			saved = applicationlogService.update(applicationlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<ApplicationLog>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> getApplicationLog(@PathVariable String id) {
		ApplicationLog applicationlog = applicationlogService.findOne(Long.parseLong(id));
		
		if(applicationlog == null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ApplicationLog>(applicationlog,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< ArrayList<ApplicationLog> > getApplicationLogs() {
		ArrayList<ApplicationLog> applicationlogs = (ArrayList<ApplicationLog>) applicationlogService.findAll();
		return new ResponseEntity< ArrayList<ApplicationLog> >(applicationlogs,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> deleteApplicationLogById(@PathVariable String id) {
		ApplicationLog applicationlog = applicationlogService.findOne(Long.parseLong(id));
		
		if(applicationlog == null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
		}

		try {
			applicationlogService.delete(applicationlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ApplicationLog>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", 
	method = RequestMethod.DELETE,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplicationLog> deleteApplicationLog(@RequestBody ApplicationLog applicationlog)
    {
		ApplicationLog exists = applicationlogService.findOne(applicationlog.getId());
		
		if(exists == null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
		}
        
		try {
			applicationlogService.delete(applicationlog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<ApplicationLog>(HttpStatus.OK);
    }
	
}

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

import com.center.domain.OperatingSystemLog;
import com.center.service.OperatingSystemLogService;

@RestController
@RequestMapping(value = "/operatingsystemlog")
public class OperatingSystemLogController {

	@Autowired
	OperatingSystemLogService operatingsystemlogService;
	
	@CrossOrigin
	@RequestMapping(value = "/create", 
	method = RequestMethod.POST,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperatingSystemLog> createOperatingSystemLog(@RequestBody OperatingSystemLog operatingsystemlog)
    {
		OperatingSystemLog exists = operatingsystemlogService.findOne(operatingsystemlog.getId());
		
		if(exists != null){
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.CONFLICT);
		}
        
		OperatingSystemLog saved = null;
		try {
			saved = operatingsystemlogService.create(operatingsystemlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<OperatingSystemLog>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(value = "/update", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperatingSystemLog> updateOperatingSystemLog(@RequestBody OperatingSystemLog operatingsystemlog)
    {
		OperatingSystemLog exists = operatingsystemlogService.findOne(operatingsystemlog.getId());
		
		if(exists == null){
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}
        
		OperatingSystemLog saved = null;
		try {
			saved = operatingsystemlogService.update(operatingsystemlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<OperatingSystemLog>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/get",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> getOperatingSystemLog(@PathVariable String id) {
		OperatingSystemLog operatingsystemlog = operatingsystemlogService.findOne(Long.parseLong(id));
		
		if(operatingsystemlog == null){
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<OperatingSystemLog>(operatingsystemlog,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/getAll",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< ArrayList<OperatingSystemLog> > getOperatingSystemLogs() {
		ArrayList<OperatingSystemLog> operatingsystemlogs = (ArrayList<OperatingSystemLog>) operatingsystemlogService.findAll();
		return new ResponseEntity< ArrayList<OperatingSystemLog> >(operatingsystemlogs,
				HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}/delete",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> deleteOperatingSystemLogById(@PathVariable String id) {
		OperatingSystemLog operatingsystemlog = operatingsystemlogService.findOne(Long.parseLong(id));
		
		if(operatingsystemlog == null){
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}

		try {
			operatingsystemlogService.delete(operatingsystemlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<OperatingSystemLog>(HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/delete", 
	method = RequestMethod.DELETE,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperatingSystemLog> deleteOperatingSystemLog(@RequestBody OperatingSystemLog operatingsystemlog)
    {
		OperatingSystemLog exists = operatingsystemlogService.findOne(operatingsystemlog.getId());
		
		if(exists == null){
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}
        
		try {
			operatingsystemlogService.delete(operatingsystemlog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<OperatingSystemLog>(HttpStatus.OK);
    }
	
}

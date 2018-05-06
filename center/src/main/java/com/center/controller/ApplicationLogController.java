package com.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.ApplicationLog;
import com.center.repository.ApplicationLogRepository;

@RestController
@RequestMapping(value = "/applicationLogs")
public class ApplicationLogController {

	@Autowired
	ApplicationLogRepository applicationlogRepository;

	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
    public ResponseEntity<ApplicationLog> createApplicationLog(@RequestBody ApplicationLog applicationlog) throws Exception
    {
		ApplicationLog saved = new ApplicationLog();
		saved.setId(applicationlog.getId());
		saved.setTimeStamp(applicationlog.getTimeStamp());
		saved.setAgent(applicationlog.getAgent());
		saved.setEventId(applicationlog.getEventId());
		saved.setPriority(applicationlog.getPriority());
		saved.setApplication(applicationlog.getApplication());
		saved.setMessageId(applicationlog.getMessageId());
		saved.setMessage(applicationlog.getMessage());
		saved = applicationlogRepository.save(saved);
		return new ResponseEntity<ApplicationLog>(saved, HttpStatus.CREATED);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/saveAll",
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<ApplicationLog>> createOperatingSystemLog(
			@RequestBody ArrayList<ApplicationLog> logs) throws Exception {
		for (ApplicationLog log : logs) {
			applicationlogRepository.save(log);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(HttpStatus.OK);
	}

	
	@CrossOrigin
	@RequestMapping(
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
    public ResponseEntity<ApplicationLog> updateApplicationLog(@RequestBody ApplicationLog applicationlog) throws Exception
    {
		ApplicationLog saved = applicationlogRepository.findOne(applicationlog.getId());
		if(saved == null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.BAD_REQUEST);
		}
		saved.setTimeStamp(applicationlog.getTimeStamp());
		saved.setAgent(applicationlog.getAgent());
		saved.setEventId(applicationlog.getEventId());
		saved.setPriority(applicationlog.getPriority());
		saved.setApplication(applicationlog.getApplication());
		saved.setMessageId(applicationlog.getMessageId());
		saved.setMessage(applicationlog.getMessage());
		saved = applicationlogRepository.save(saved);
		return new ResponseEntity<ApplicationLog>(saved, HttpStatus.OK);
    }
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ApplicationLog> getApplicationLog(@PathVariable String id) {
		ApplicationLog applicationLog = applicationlogRepository.findOne(id);
		if(applicationLog == null){
			return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApplicationLog>(applicationLog, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogs() {
		ArrayList<ApplicationLog> applicationLogs = (ArrayList<ApplicationLog>) applicationlogRepository.findAll();
		return new ResponseEntity< ArrayList<ApplicationLog> >(applicationLogs, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ApplicationLog> deleteApplicationLogById(@PathVariable String id) throws Exception {
		ApplicationLog applicationLog = applicationlogRepository.findOne(id);
		if(applicationLog != null){
			applicationlogRepository.delete(id);
			return new ResponseEntity<ApplicationLog>(HttpStatus.OK);
		}
		return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			params = "applicationName",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogsByApplicationName(@RequestParam String applicationName){
		ArrayList<ApplicationLog> logs = applicationlogRepository.findApplicationLogByApplication(applicationName);
		if(logs.isEmpty()){
			return new ResponseEntity<ArrayList<ApplicationLog>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(logs, HttpStatus.OK);
	}
	
	@RequestMapping(
			params = "priority",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogsByPriority(@RequestParam String priority){
		ArrayList<ApplicationLog> logs = applicationlogRepository.findApplicationLogByPriority(priority);
		if(logs.isEmpty()){
			return new ResponseEntity<ArrayList<ApplicationLog>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(logs, HttpStatus.OK);
	}
	
	@RequestMapping(
			params = "timeStamp",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogsByTimeStamp(@RequestParam String timeStamp) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = (Date) simpleDateFormat.parse(timeStamp);
		ArrayList<ApplicationLog> logs = applicationlogRepository.findApplicationLogByTimeStamp(date);
		if(logs.isEmpty()){
			return new ResponseEntity<ArrayList<ApplicationLog>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(logs, HttpStatus.OK);
	}
	
}
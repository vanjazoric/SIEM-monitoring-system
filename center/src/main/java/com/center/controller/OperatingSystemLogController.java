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

import com.center.domain.LogServer;
import com.center.domain.OperatingSystemLog;
import com.center.repository.OperatingSystemLogRepository;

@RestController
@RequestMapping(value = "/OSlogs")
public class OperatingSystemLogController {

	@Autowired
	OperatingSystemLogRepository OSlogRepository;

	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<OperatingSystemLog>> createOperatingSystemLog(
			@RequestBody ArrayList<OperatingSystemLog> logs) throws Exception {
		for (OperatingSystemLog log : logs) {
			System.out.println(log);
			OSlogRepository.insert(log);
		}
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(HttpStatus.OK);
	}
	@CrossOrigin
	@RequestMapping(
		value = "/create", 
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
    public ResponseEntity<OperatingSystemLog> createOperatingSystemLog(@RequestBody OperatingSystemLog operatingSystemLog)
    {
		OperatingSystemLog saved = new OperatingSystemLog();
		saved.setTimeStamp(operatingSystemLog.getTimeStamp());
		saved.setAgent(operatingSystemLog.getAgent());
		saved.setLevel(operatingSystemLog.getLevel());
		saved.setEventId(operatingSystemLog.getEventId());
		saved.setTaskCategory(operatingSystemLog.getTaskCategory());
		saved.setSource(operatingSystemLog.getSource());
		saved = OSlogRepository.insert(saved);
        return new ResponseEntity<OperatingSystemLog>(saved, HttpStatus.CREATED);
    }

	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> updateOperatingSystemLog(
			@RequestBody OperatingSystemLog operatingsystemlog) {
		OperatingSystemLog exists = OSlogRepository
				.findOne(operatingsystemlog.getId());

		if (exists == null) {
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}

		OperatingSystemLog saved = null;
		try {
			saved = OSlogRepository.save(operatingsystemlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<OperatingSystemLog>(saved, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> getOperatingSystemLog(
			@PathVariable String id) {
		OperatingSystemLog operatingsystemlog = OSlogRepository
				.findOne(id);

		if (operatingsystemlog == null) {
			return new ResponseEntity<OperatingSystemLog>(
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<OperatingSystemLog>(operatingsystemlog,
				HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<OperatingSystemLog>> getOperatingSystemLogs() {
		ArrayList<OperatingSystemLog> operatingsystemlogs = (ArrayList<OperatingSystemLog>) OSlogRepository
				.findAll();
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(
				operatingsystemlogs, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> deleteOperatingSystemLogById(
			@PathVariable String id) {
		OperatingSystemLog operatingsystemlog = OSlogRepository
				.findOne(id);

		if (operatingsystemlog == null) {
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}

		try {
			OSlogRepository.delete(operatingsystemlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<OperatingSystemLog>(HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> deleteOperatingSystemLog(
			@RequestBody OperatingSystemLog operatingsystemlog) {
		OperatingSystemLog exists = OSlogRepository
				.findOne(operatingsystemlog.getId());

		if (exists == null) {
			return new ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND);
		}

		try {
			OSlogRepository.delete(operatingsystemlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<OperatingSystemLog>(HttpStatus.OK);
	}
	
	@RequestMapping(
			params = "timeStamp",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<OperatingSystemLog>> getOperatingSystemLogsByTimeStamp(@RequestParam String timeStamp) throws ParseException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = (Date) simpleDateFormat.parse(timeStamp);
		ArrayList<OperatingSystemLog> logs = OSlogRepository.findOperatingSystemByTimeStamp(date);
		if(logs.isEmpty()){
			return new ResponseEntity<ArrayList<OperatingSystemLog>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(logs, HttpStatus.OK);
	}

}

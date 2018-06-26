package com.center.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.Alarm;
import com.center.repository.AlarmRepository;

@RestController
@RequestMapping(value = "/alarms")
public class AlarmController {
	
	@Autowired
	AlarmRepository alarmRepository;
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Alarm> createAlarm(@RequestBody Alarm alarm){
		Alarm saved = new Alarm();
		saved.setDescription(alarm.getDescription());
		saved.setNumOfItems(alarm.getNumOfItems());
		saved.setDuration(alarm.getDuration());
		saved.setConditions(alarm.getConditions());
		saved = alarmRepository.insert(saved);
		return new ResponseEntity<Alarm>(saved, HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(
		method = RequestMethod.PUT,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Alarm> updateAlarm(@RequestBody Alarm alarm){
		Alarm saved = alarmRepository.findOne(alarm.getId());
		if(saved == null){
			return new ResponseEntity<Alarm>(HttpStatus.BAD_REQUEST);
		}
		saved.setDescription(alarm.getDescription());
		saved.setNumOfItems(alarm.getNumOfItems());
		saved.setDuration(alarm.getDuration());
		saved.setConditions(alarm.getConditions());
		saved = alarmRepository.save(saved);
		return new ResponseEntity<Alarm>(saved, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Alarm>> getAlarms(){
		ArrayList<Alarm> alarms = (ArrayList<Alarm>) alarmRepository.findAll();
		return new ResponseEntity<ArrayList<Alarm>>(alarms, HttpStatus.OK);
	}
	
	/*@CrossOrigin
	@RequestMapping(
			value = "/"
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Alarm> triggerAlarm(){
		
	}*/

}

package com.center.controller;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.Alarm;
import com.center.domain.Log;
import com.center.domain.Report;
import com.center.repository.LogRepository;
import com.center.repository.ReportRepository;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RestController
@RequestMapping(value = "/reports")
public class ReportController {
	
	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	
	@RequestMapping(
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Report> createReport(@RequestBody Report report, @RequestHeader HttpHeaders header){
		Report saved = new Report();
		if(report.getItems().equals("Log")){
			if(report.getCondition().equals("Machine")){
				ArrayList<Log> logs = (ArrayList<Log>) logRepository.findByAgentName(report.getMachineName());
				int counter = 0;
				for (Log log : logs) {
					if((log.getTimeStamp().after(report.getStartDate())) && (log.getTimeStamp().before(report.getEndDate()))){
						counter++;
					}
				}
				saved.setTitle(report.getTitle());
				saved.setStartDate(report.getStartDate());
				saved.setEndDate(report.getEndDate());
				saved.setCondition(report.getCondition());
				saved.setItems(report.getItems());
				saved.setMachineName(report.getMachineName());
				saved.setAmount(counter);
				saved = reportRepository.save(saved);
				return new ResponseEntity<Report>(saved, HttpStatus.CREATED);
			}else{
				ArrayList<Log> logs = (ArrayList<Log>) logRepository.findAll();
				int counter = 0;
				for (Log log : logs) {
					if((log.getTimeStamp().after(report.getStartDate())) && (log.getTimeStamp().before(report.getEndDate()))){
						counter++;
					}
				}
				saved.setTitle(report.getTitle());
				saved.setStartDate(report.getStartDate());
				saved.setEndDate(report.getEndDate());
				saved.setCondition(report.getCondition());
				saved.setItems(report.getItems());
				saved.setAmount(counter);
				saved = reportRepository.save(saved);
				return new ResponseEntity<Report>(saved, HttpStatus.CREATED);
			}
		}else{
			if(report.getCondition().equals("Machine")){
				MongoClient mongoClient = new MongoClient("localhost", 27017);
				DB db = mongoClient.getDB("LogDB");
				DBCollection collection = db.getCollection("triggerdAlarms");
				DBCursor cursor = collection.find();
				ArrayList<DBObject> alarms = (ArrayList<DBObject>) cursor.toArray();
				ArrayList<Alarm> allAlarms = new ArrayList<Alarm>();
				for (DBObject dbObject : alarms) {
					Date date = (Date) dbObject.get("date");
					String agentName = (String) dbObject.get("agentName");
					Alarm alarm = new Alarm();
					alarm.setDate(date);
					alarm.setAgentName(agentName);
					allAlarms.add(alarm);
				}
				int counter = 0;
				for (Alarm alarm : allAlarms) {
					if((alarm.getAgentName().equals(report.getMachineName())) && (alarm.getDate().after(report.getStartDate())) && (alarm.getDate().before(report.getEndDate()))){
						counter++;
					}
				}
				saved.setTitle(report.getTitle());
				saved.setStartDate(report.getStartDate());
				saved.setEndDate(report.getEndDate());
				saved.setCondition(report.getCondition());
				saved.setItems(report.getItems());
				saved.setMachineName(report.getMachineName());
				saved.setAmount(counter);
				saved = reportRepository.save(saved);
				return new ResponseEntity<Report>(saved, HttpStatus.CREATED);
			}else{
				MongoClient mongoClient = new MongoClient("localhost", 27017);
				DB db = mongoClient.getDB("LogDB");
				DBCollection collection = db.getCollection("triggerdAlarms");
				DBCursor cursor = collection.find();
				ArrayList<DBObject> alarms = (ArrayList<DBObject>) cursor.toArray();
				ArrayList<Alarm> allAlarms = new ArrayList<Alarm>();
				for (DBObject dbObject : alarms) {
					Date date = (Date) dbObject.get("date");
					Alarm alarm = new Alarm();
					alarm.setDate(date);
					allAlarms.add(alarm);
				}
				int counter = 0;
				for (Alarm alarm : allAlarms) {
					if((alarm.getDate().after(report.getStartDate())) && (alarm.getDate().before(report.getEndDate()))){
						counter++;
					}
				}
				saved.setTitle(report.getTitle());
				saved.setStartDate(report.getStartDate());
				saved.setEndDate(report.getEndDate());
				saved.setCondition(report.getCondition());
				saved.setItems(report.getItems());
				saved.setAmount(counter);
				saved = reportRepository.save(saved);
				return new ResponseEntity<Report>(saved, HttpStatus.CREATED);
			}
		}
	}
	
	@RequestMapping(
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<ArrayList<Report>> getReports(){
		ArrayList<Report> reports = (ArrayList<Report>) reportRepository.findAll();
		return new ResponseEntity<ArrayList<Report>>(reports, HttpStatus.OK);
	}

}
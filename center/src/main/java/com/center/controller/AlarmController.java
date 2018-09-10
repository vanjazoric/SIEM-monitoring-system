package com.center.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.Alarm;
import com.center.service.AlarmService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RestController
@RequestMapping(value = "/alarms")
public class AlarmController {

	@Autowired
	private AlarmService alarmService;

	@Autowired
	private SimpMessagingTemplate template;

	@PreAuthorize("hasAuthority('WRITE_ALARMS')")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Alarm> createAlarm(@RequestBody Alarm alarm) {
		Alarm saved = new Alarm();
		saved.setDescription(alarm.getDescription());
		saved.setNumOfItems(alarm.getNumOfItems());
		saved.setDuration(alarm.getDuration());
		saved.setConditions(alarm.getConditions());
		saved.setType(alarm.getType());
		saved = alarmService.save(saved);
		return new ResponseEntity<Alarm>(saved, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('WRITE_ALARMS')")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Alarm> updateAlarm(@RequestBody Alarm alarm) {
		Alarm saved = alarmService.findOne(alarm.getId());
		if (saved == null) {
			return new ResponseEntity<Alarm>(HttpStatus.BAD_REQUEST);
		}
		saved.setDescription(alarm.getDescription());
		saved.setNumOfItems(alarm.getNumOfItems());
		saved.setDuration(alarm.getDuration());
		saved.setConditions(alarm.getConditions());
		saved.setType(alarm.getType());
		saved = alarmService.save(saved);
		return new ResponseEntity<Alarm>(saved, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('READ_ALARMS')")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Alarm>> getAlarms() {
		ArrayList<Alarm> alarms = (ArrayList<Alarm>) alarmService.findAll();
		return new ResponseEntity<ArrayList<Alarm>>(alarms, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('DELETE_ALARMS')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteAlarm(@PathVariable String id) {
		Alarm alarm = alarmService.findOne(id);
		if (alarm == null) {
			return new ResponseEntity<String>("Alarm is not deleted!", HttpStatus.BAD_REQUEST);
		}
		alarmService.delete(alarm);
		System.out.println("Alarm je obrisan iz baze!");
		return new ResponseEntity<String>("Alarm is deleted!", HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('READ_FIRED_ALARMS')")
	@RequestMapping(value = "/triggeredAlarms", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Alarm>> getTriggeredAlarms() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("LogDB");
		DBCollection collection = db.getCollection("triggerdAlarms");
		DBCursor cursor = collection.find();
		ArrayList<DBObject> alarms = (ArrayList<DBObject>) cursor.toArray();
		ArrayList<Alarm> allAlarms = new ArrayList<Alarm>();
		for (DBObject dbObject : alarms) {
			String description = (String) dbObject.get("description");
			int numOfItems = (int) dbObject.get("numOfItems");
			int duration = (int) dbObject.get("duration");
			Date date = (Date) dbObject.get("date");
			Alarm alarm = new Alarm();
			alarm.setDate(date);
			alarm.setDuration(duration);
			alarm.setDescription(description);
			alarm.setNumOfItems(numOfItems);
			allAlarms.add(alarm);
		}
		return new ResponseEntity<ArrayList<Alarm>>(allAlarms, HttpStatus.OK);
	}

	public void triggerAlarm() {
		System.out.println("Pocetak funkcije");
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB db = mongoClient.getDB("LogDB");
		DBCollection logsCollection = db.getCollection("logs");

		while (true) {
			ArrayList<Alarm> alarms = (ArrayList<Alarm>) alarmService.findAll();
			System.out.println("Broj alarma: " + alarms.size());
			if (!alarms.isEmpty()) {
				alarms.stream().parallel().forEach((alarm) -> {
					try {
						System.out.println("Pocetak uspavljivanja");
						Thread.sleep(alarm.getDuration());
						System.out.println("Uspavljivanje zavrseno");
						System.out.println("Id alarma: " + alarm.getId());
						System.out.println("Kursor: " + alarm.getCursor());
						DBCursor cursor = logsCollection.find().skip(alarm.getCursor());
						List<DBObject> all = cursor.toArray();
						for (DBObject dbObject : all) {
							System.out.println("logovi iz baze: " + dbObject);
						}
						System.out.println("-----------------");
						int length = all.size();
						System.out.println("Broj logova: " + length);
						int counter = 0;
						int counterMachine = 0;
						int counterUsername = 0;
						for (DBObject dbObject : all) {
							String agentName = (String) dbObject.get("agentName");
							System.out.println("Ime agenta: " + agentName);
							alarm.setAgentName(agentName);
							if (alarm.getType().contains("Machine") && dbObject.toString().toLowerCase().contains("machine")) {
								System.out.println("ALARM");
								System.out.println("BROJ" + alarm.getNumOfItems());
								counterMachine++;
							} else if (alarm.getType().contains("Username") && dbObject.toString().toLowerCase().contains("username")) {
								counterUsername++;
							} else if (dbObject.toString().toLowerCase().contains(alarm.getConditions().toLowerCase().split(";")[1])) {
								counter++;
							}
						}
						System.out.println("broj logova koji zadovoljavaju uslov: " + counter);
						if (counter == alarm.getNumOfItems() || counterMachine == alarm.getNumOfItems() || counterUsername == alarm.getNumOfItems()) {
							BasicDBObject basicDBObject = new BasicDBObject();
							basicDBObject.put("id", alarm.getId());
							basicDBObject.put("numOfItems", alarm.getNumOfItems());
							basicDBObject.put("description", alarm.getDescription());
							basicDBObject.put("duration", alarm.getDuration());
							basicDBObject.put("conditions", alarm.getConditions());
							basicDBObject.put("date", new Date());
							basicDBObject.put("agentName", alarm.getAgentName());
							template.convertAndSend("/logs/alarms", alarm);
							if (db.collectionExists("triggerdAlarms")) {
								DBCollection triggeredAlarmsCollection = db.getCollection("triggerdAlarms");
								triggeredAlarmsCollection.insert(basicDBObject);
								System.out.println("Alarm je okinut");
							} else {
								DBCollection triggeredAlarmsCollection = db.createCollection("triggerdAlarms",
										new BasicDBObject());
								triggeredAlarmsCollection.insert(basicDBObject);
								System.out.println("Alarm je okinut");
							}
						}
						alarm.setCursor(alarm.getCursor() + length);
						alarmService.save(alarm);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			}
		}
	}

}
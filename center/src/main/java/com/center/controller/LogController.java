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

import com.center.domain.Log;
import com.center.domain.OperatingSystemLog;
import com.center.repository.ApplicationLogRepository;
import com.center.repository.LogFirewallRepository;
import com.center.repository.LogRepository;
import com.center.repository.LogServerRepository;
import com.center.repository.OperatingSystemLogRepository;

@RestController
@RequestMapping(value = "/api/logs")
public class LogController {

	@Autowired
	LogRepository logRepository;

	@Autowired
	OperatingSystemLogRepository osLogRep;

	@Autowired
	ApplicationLogRepository appLogRep;

	@Autowired
	LogServerRepository serverLogRep;

	@Autowired
	LogFirewallRepository firewallLogRep;

	@CrossOrigin
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Log> createLog(@RequestBody Log log) {
		Log exists = logRepository.findOne(log.getId());

		if (exists != null) {
			return new ResponseEntity<Log>(HttpStatus.CONFLICT);
		}

		Log saved = null;
		try {
			saved = logRepository.insert(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Log>(saved, HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Log> updateLog(@RequestBody Log log) {
		Log exists = logRepository.findOne(log.getId());

		if (exists == null) {
			return new ResponseEntity<Log>(HttpStatus.NOT_FOUND);
		}

		Log saved = null;
		try {
			saved = logRepository.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Log>(saved, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Log> getLog(@PathVariable String id) {
		Log log = logRepository.findOne(id);

		if (log == null) {
			return new ResponseEntity<Log>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Log>(log, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Log>> getLogs() {
		System.out.println("STIGAO U CENTAR");
		ArrayList<Log> logs = (ArrayList<Log>) logRepository.findAll();
		return new ResponseEntity<ArrayList<Log>>(logs, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Log> deleteLogById(@PathVariable String id) {
		Log log = logRepository.findOne(id);

		if (log == null) {
			return new ResponseEntity<Log>(HttpStatus.NOT_FOUND);
		}

		try {
			logRepository.delete(log);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Log>(HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Log> deleteLog(@RequestBody Log log) {
		Log exists = logRepository.findOne(log.getId());

		if (exists == null) {
			return new ResponseEntity<Log>(HttpStatus.NOT_FOUND);
		}

		try {
			logRepository.delete(log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<Log>(HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Log>> searchlogs(
			@PathVariable String startDate, @PathVariable String endDate)
			throws ParseException {
		System.out.println(startDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm");
		Date start = (Date) simpleDateFormat.parse(startDate);
		Date end = (Date) simpleDateFormat.parse(endDate);
		ArrayList<Log> logs = (ArrayList<Log>) logRepository.findAll();
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<Log>>(HttpStatus.NOT_FOUND);
		}
		System.out.println(start);
		ArrayList<Log> finded = new ArrayList<Log>();
		for (Log log : logs) {
			if (log.getTimeStamp().after(start)
					&& log.getTimeStamp().before(end)) {
				finded.add(log);
			}
		}
		System.out.println(finded.size());
		return new ResponseEntity<ArrayList<Log>>(finded, HttpStatus.OK);
	}

}

package com.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@Autowired
    private SimpMessagingTemplate template;
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('WRITE_LOGS')")
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
	//@PreAuthorize("hasAuthority('WRITE_LOGS')")
    public ResponseEntity<OperatingSystemLog> createOperatingSystemLog(@RequestBody OperatingSystemLog operatingSystemLog)
    {
		OperatingSystemLog saved = new OperatingSystemLog();
		saved.setTimeStamp(operatingSystemLog.getTimeStamp());
		saved.setAgentName(operatingSystemLog.getAgentName());
		saved.setLevel(operatingSystemLog.getLevel());
		saved.setEventId(operatingSystemLog.getEventId());
		saved.setTaskCategory(operatingSystemLog.getTaskCategory());
		saved.setSource(operatingSystemLog.getSource());
		saved = OSlogRepository.insert(saved);
		template.convertAndSend("/logs/osLogs", saved);
        return new ResponseEntity<OperatingSystemLog>(saved, HttpStatus.CREATED);
    }

	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> updateOperatingSystemLog(
			@RequestBody OperatingSystemLog operatingsystemlog) {
		OperatingSystemLog exists = OSlogRepository.findOne(operatingsystemlog
				.getId());

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
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<OperatingSystemLog> getOperatingSystemLog(
			@PathVariable String id) {
		OperatingSystemLog operatingsystemlog = OSlogRepository.findOne(id);

		if (operatingsystemlog == null) {
			return new ResponseEntity<OperatingSystemLog>(
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<OperatingSystemLog>(operatingsystemlog,
				HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<OperatingSystemLog>> getOperatingSystemLogs() {
		ArrayList<OperatingSystemLog> operatingsystemlogs = (ArrayList<OperatingSystemLog>) OSlogRepository
				.findAllByClass("log_OS");
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(
				operatingsystemlogs, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> deleteOperatingSystemLogById(
			@PathVariable String id) {
		OperatingSystemLog operatingsystemlog = OSlogRepository.findOne(id);

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
		OperatingSystemLog exists = OSlogRepository.findOne(operatingsystemlog
				.getId());

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

	@CrossOrigin
	@RequestMapping(params = "level", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<OperatingSystemLog>> getOperatingSystemLogByLevel(
			@RequestParam(value = "level") String level) {
		ArrayList<OperatingSystemLog> logs = new ArrayList<OperatingSystemLog>();
		String p1 = level.toUpperCase();
		String p2 = "";
		String p3 = "";
		if (level.contains("|")) {
			String[] levels = level.replaceAll("\\s", "").split("\\|");
			p1 = levels[0];
			if (!levels[1].equals("")) {
				p2 = levels[1];
			}
			if (levels.length > 2) {
				if (!levels[2].equals("")) {
					p3 = levels[2];
				}
			}
		}
			logs = OSlogRepository.findOperatingSystemLogByLevel(p1, p2, p3);
			if (logs.isEmpty()) {
				return new ResponseEntity<ArrayList<OperatingSystemLog>>(
						HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(logs,
				HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(params = "source", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<OperatingSystemLog>> getOperatingSystemLogBySource(
			@RequestParam(value = "source") String method) {
		System.out.println(method);
		ArrayList<OperatingSystemLog> logs = OSlogRepository
				.findOperatingSystemLogBySource(method);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<OperatingSystemLog>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(logs,
				HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(params = "eventId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<OperatingSystemLog>> getOperatingSystemLogByEventId(
			@RequestParam(value = "eventId") int eventId) {
		ArrayList<OperatingSystemLog> logs = OSlogRepository
				.findOperatingSystemLogByEventId(eventId);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<OperatingSystemLog>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(logs,
				HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<OperatingSystemLog>> searchlogs(
			@PathVariable String startDate, @PathVariable String endDate)
			throws ParseException {
		System.out.println(startDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm");
		Date start = (Date) simpleDateFormat.parse(startDate);
		Date end = (Date) simpleDateFormat.parse(endDate);
		ArrayList<OperatingSystemLog> logs = OSlogRepository
				.findAllByClass("OS_logs");
		System.out.println(start);
		ArrayList<OperatingSystemLog> finded = new ArrayList<OperatingSystemLog>();
		for (OperatingSystemLog log : logs) {
			if (log.getTimeStamp().after(start)
					&& log.getTimeStamp().before(end)) {
				finded.add(log);
			}
		}
		System.out.println(finded.size());
		return new ResponseEntity<ArrayList<OperatingSystemLog>>(finded,
				HttpStatus.OK);
	}

}

package com.center.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.ApplicationLog;
import com.center.repository.ApplicationLogRepository;
import com.center.repository.LogRepository;

@RestController
@RequestMapping(value = "/applicationLogs")
public class ApplicationLogController {

	@Autowired
	ApplicationLogRepository applicationlogRepository;
	@Autowired
	LogRepository logRepository;
	@Autowired
    private SimpMessagingTemplate template;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> createApplicationLog(
			@RequestBody ApplicationLog applicationlog) throws Exception {
		System.out.println("USAO U CENTAR APP LOG KONTROLER");
		ApplicationLog saved = new ApplicationLog();
		saved.setTimeStamp(applicationlog.getTimeStamp());
		saved.setAgentName(applicationlog.getAgentName());
		saved.setEventId(applicationlog.getEventId());
		saved.setPriority(applicationlog.getPriority());
		saved.setApplication(applicationlog.getApplication());
		saved.setMessageId(applicationlog.getMessageId());
		saved.setMessage(applicationlog.getMessage());
		saved = applicationlogRepository.insert(saved);
		template.convertAndSend("/logs/applogs", saved);
		return new ResponseEntity<ApplicationLog>(saved, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/saveAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ApplicationLog>> createOperatingSystemLog(
			@RequestParam ArrayList<ApplicationLog> logs) throws Exception {
		for (ApplicationLog log : logs) {
			applicationlogRepository.save(log);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> updateApplicationLog(
			@RequestParam ApplicationLog applicationlog) throws Exception {
		ApplicationLog saved = applicationlogRepository.findOne(applicationlog
				.getId());
		if (saved == null) {
			return new ResponseEntity<ApplicationLog>(HttpStatus.BAD_REQUEST);
		}
		saved.setTimeStamp(applicationlog.getTimeStamp());
		saved.setAgentName(applicationlog.getAgentName());
		saved.setEventId(applicationlog.getEventId());
		saved.setPriority(applicationlog.getPriority());
		saved.setApplication(applicationlog.getApplication());
		saved.setMessageId(applicationlog.getMessageId());
		saved.setMessage(applicationlog.getMessage());
		saved = applicationlogRepository.save(saved);
		return new ResponseEntity<ApplicationLog>(saved, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ApplicationLog> getApplicationLog(
			@PathVariable String id) {
		ApplicationLog applicationLog = applicationlogRepository.findOne(id);
		if (applicationLog == null) {
			return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApplicationLog>(applicationLog, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<List<ApplicationLog>> getApplicationLogs() {
		List<ApplicationLog> applicationLogs = applicationlogRepository
				.findAllByClass("log_app");
		return new ResponseEntity<List<ApplicationLog>>(applicationLogs,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> deleteApplicationLogById(
			@PathVariable String id) throws Exception {
		ApplicationLog applicationLog = applicationlogRepository.findOne(id);
		if (applicationLog != null) {
			applicationlogRepository.delete(id);
			return new ResponseEntity<ApplicationLog>(HttpStatus.OK);
		}
		return new ResponseEntity<ApplicationLog>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(params = "app", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogsByApplicationName(
			@RequestParam(value = "app") String applicationName) {
		ArrayList<ApplicationLog> logs = applicationlogRepository
				.findApplicationLogByApplication(applicationName);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<ApplicationLog>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(logs,
				HttpStatus.OK);
	}

	@RequestMapping(params = "message", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogsByMessage(
			@RequestParam(value = "message") String message) {
		ArrayList<ApplicationLog> logs = applicationlogRepository
				.findApplicationLogByMessage(message);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<ApplicationLog>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(logs,
				HttpStatus.OK);
	}

	@RequestMapping(params = "priority", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<ApplicationLog>> getApplicationLogsByPriority(
			@RequestParam(value = "priority") String priority) {

		ArrayList<ApplicationLog> logs = new ArrayList<ApplicationLog>();
		String p1 = priority;
		String p2 = "";
		String p3 = "";
		if (priority.contains("|")) {
			String[] levels = priority.replaceAll("\\s", "").split("\\|");
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
			logs = applicationlogRepository.findApplicationLogByPriority(p1,
					p2, p3);
			if (logs.isEmpty()) {
				return new ResponseEntity<ArrayList<ApplicationLog>>(
						HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(logs,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<ApplicationLog>> searchlogs(
			@PathVariable String startDate, @PathVariable String endDate)
			throws ParseException {
		System.out.println(startDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm");
		Date start = (Date) simpleDateFormat.parse(startDate);
		Date end = (Date) simpleDateFormat.parse(endDate);
		ArrayList<ApplicationLog> logs = applicationlogRepository
				.findAllByClass("log_app");
		System.out.println(start);
		ArrayList<ApplicationLog> finded = new ArrayList<ApplicationLog>();
		for (ApplicationLog log : logs) {
			if (log.getTimeStamp().after(start)
					&& log.getTimeStamp().before(end)) {
				finded.add(log);
			}
		}
		System.out.println(finded.size());
		if (finded.isEmpty()) {
			return new ResponseEntity<ArrayList<ApplicationLog>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<ApplicationLog>>(finded,
				HttpStatus.OK);
	}
}

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.center.domain.LogServer;
import com.center.repository.LogServerRepository;

@RestController
@RequestMapping(value = "/logserver")
public class LogServerController {

	@Autowired
	LogServerRepository logserverRepository;
	@Autowired
    private SimpMessagingTemplate template;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	//@PreAuthorize("hasAuthority('WRITE_LOGS')")
	public ResponseEntity<LogServer> createLogServer(
			@RequestBody LogServer logserver) {
		LogServer saved = new LogServer();
		saved.setTimeStamp(logserver.getTimeStamp());
		saved.setAgentName(logserver.getAgentName());
		saved.setClientIp(logserver.getClientIp());
		saved.setLogHost(logserver.getLogHost());
		saved.setMessageId(logserver.getMessageId());
		saved.setMethod(logserver.getMethod());
		saved.setResourceRequest(logserver.getResourceRequest());
		saved.setHttpStatus(logserver.getHttpStatus());
		saved.setSizeOfReturnedObj(logserver.getSizeOfReturnedObj());
		saved = logserverRepository.insert(saved);
		template.convertAndSend("/logs/serverLogs", saved);
		return new ResponseEntity<LogServer>(saved, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogServer> updateLogServer(
			@RequestBody LogServer logserver) {
		LogServer exists = logserverRepository.findOne(logserver.getId());

		if (exists == null) {
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}

		LogServer saved = null;
		try {
			saved = logserverRepository.save(logserver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LogServer>(saved, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<LogServer> getLogServer(@PathVariable String id) {
		LogServer logserver = logserverRepository.findOne(id);

		if (logserver == null) {
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<LogServer>(logserver, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<LogServer>> getLogServers() {
		ArrayList<LogServer> logservers = (ArrayList<LogServer>) logserverRepository
				.findAllByClass("log_server");
		System.out.println(logservers.size());
		return new ResponseEntity<ArrayList<LogServer>>(logservers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogServer> deleteLogServerById(@PathVariable String id) {
		LogServer logserver = logserverRepository.findOne(id);

		if (logserver == null) {
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}

		try {
			logserverRepository.delete(logserver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LogServer>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogServer> deleteLogServer(
			@RequestBody LogServer logserver) {
		LogServer exists = logserverRepository.findOne(logserver.getId());

		if (exists == null) {
			return new ResponseEntity<LogServer>(HttpStatus.NOT_FOUND);
		}

		try {
			logserverRepository.delete(logserver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<LogServer>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<LogServer>> searchlogs(
			@PathVariable String startDate, @PathVariable String endDate)
			throws ParseException {
		System.out.println(startDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm");
		Date start = (Date) simpleDateFormat.parse(startDate);
		Date end = (Date) simpleDateFormat.parse(endDate);
		ArrayList<LogServer> logs = logserverRepository
				.findAllByClass("log_server");
		System.out.println(start);
		ArrayList<LogServer> finded = new ArrayList<LogServer>();
		for (LogServer log : logs) {
			if (log.getTimeStamp().after(start)
					&& log.getTimeStamp().before(end)) {
				finded.add(log);
			}
		}
		System.out.println(finded.size());
		return new ResponseEntity<ArrayList<LogServer>>(finded, HttpStatus.OK);
	}

	@RequestMapping(params = "ip", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<LogServer>> getLogServerByIP(
			@RequestParam(value = "ip") String ip) {
		ArrayList<LogServer> logs = logserverRepository
				.findLogServerByClientIp(ip);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<LogServer>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<LogServer>>(logs, HttpStatus.OK);
	}

	@RequestMapping(params = "method", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<LogServer>> getLogServerByMethod(
			@RequestParam(value = "method") String method) {
		ArrayList<LogServer> logs = new ArrayList<LogServer>();
		System.out.println(method);
		String p1 = method.toUpperCase();
		String p2 = "";
		String p3 = "";
		if (method.contains("|")) {
			String[] methods = method.replaceAll("\\s", "").split("\\|");
			p1 = methods[0];
			if (!methods[1].equals("")) {
				p2 = methods[1];
			}
			if (methods.length > 2) {
				if (!methods[2].equals("")) {
					p3 = methods[2];
				}
			}
		}
		logs = logserverRepository.findLogServerByMethod(p1, p2, p3);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<LogServer>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<LogServer>>(logs, HttpStatus.OK);
	}

	@RequestMapping(params = "http", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('READ_LOGS')")
	public ResponseEntity<ArrayList<LogServer>> getLogServerByHTTP(
			@RequestParam(value = "http") String http) {
		ArrayList<LogServer> logs = new ArrayList<LogServer>();
		String p1 = http;
		int p2 = 0;
		int p3 = 0;
		if (http.contains("|")) {
			String[] tokens = http.replaceAll("\\s", "").split("\\|");
			p1 = tokens[0];
			if (!tokens[1].equals("")) {
				p2 = Integer.parseInt(tokens[1]);
			}
			if (tokens.length > 2) {
				if (!tokens[2].equals("")) {
					p3 = Integer.parseInt(tokens[2]);
				}
			}
		}
		System.out.println(logs);
		logs = logserverRepository.findLogServerByHttpStatus(
				Integer.parseInt(p1), p2, p3);
		System.out.println(logs.size());
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<LogServer>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<LogServer>>(logs, HttpStatus.OK);
	}

}

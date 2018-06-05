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
import com.center.domain.LogFirewall;
import com.center.repository.LogFirewallRepository;

@RestController
@RequestMapping(value = "/logfirewall")
public class LogFirewallController {

	@Autowired
	LogFirewallRepository logFirewallRepository;

	@CrossOrigin
	@RequestMapping(value = "/createall", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> createFirewallLogs(
			@RequestBody ArrayList<LogFirewall> logs) throws Exception {
		for (LogFirewall log : logs) {
			logFirewallRepository.insert(log);
		}
		return new ResponseEntity<ArrayList<LogFirewall>>(HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(
		value = "/create", 
		method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
    public ResponseEntity<LogFirewall> createLogFirewall(@RequestBody LogFirewall logfirewall)
    {
		LogFirewall saved = new LogFirewall();
		saved.setTimeStamp(logfirewall.getTimeStamp());
		saved.setAgent(logfirewall.getAgent());
		saved.setAction(logfirewall.getAction());
		saved.setProtocol(logfirewall.getProtocol());
		saved.setSrcIp(logfirewall.getSrcIp());
		saved.setDstIp(logfirewall.getDstIp());
		saved.setSrcPort(logfirewall.getSrcPort());
		saved.setDstPort(logfirewall.getDstPort());
		saved.setSize(logfirewall.getSize());
		saved.setTcpflags(logfirewall.getTcpflags());
		saved.setTcpsync(logfirewall.getTcpsync());
		saved = logFirewallRepository.insert(saved);
		return new ResponseEntity<LogFirewall>(saved, HttpStatus.CREATED);
		
    }

	@CrossOrigin
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> updateLogFirewall(
			@RequestBody LogFirewall logfirewall) {
		LogFirewall exists = logFirewallRepository.findOne(logfirewall.getId());

		if (exists == null) {
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		LogFirewall saved = null;
		try {
			saved = logFirewallRepository.save(logfirewall);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LogFirewall>(saved, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> getLogFirewall(@PathVariable String id) {
		LogFirewall logfirewall = logFirewallRepository.findOne(id);

		if (logfirewall == null) {
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<LogFirewall>(logfirewall, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> getLogFirewalls() {
		ArrayList<LogFirewall> logfirewalls = (ArrayList<LogFirewall>) logFirewallRepository
				.findAllByClass("log_firewall");
		return new ResponseEntity<ArrayList<LogFirewall>>(logfirewalls,
				HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> deleteLogFirewallById(
			@PathVariable String id) {
		LogFirewall logfirewall = logFirewallRepository.findOne(id);

		if (logfirewall == null) {
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		try {
			logFirewallRepository.delete(logfirewall);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<LogFirewall>(HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> deleteLogFirewall(
			@RequestBody LogFirewall logfirewall) {
		LogFirewall exists = logFirewallRepository.findOne(logfirewall.getId());

		if (exists == null) {
			return new ResponseEntity<LogFirewall>(HttpStatus.NOT_FOUND);
		}

		try {
			logFirewallRepository.delete(logfirewall);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<LogFirewall>(HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/{startDate}/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> searchlogs(
			@PathVariable String startDate, @PathVariable String endDate)
			throws ParseException {
		System.out.println(startDate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm");
		Date start = (Date) simpleDateFormat.parse(startDate);
		Date end = (Date) simpleDateFormat.parse(endDate);
		ArrayList<LogFirewall> logs = logFirewallRepository
				.findAllByClass("log_firewall");
		System.out.println(start);
		ArrayList<LogFirewall> finded = new ArrayList<LogFirewall>();
		for (LogFirewall log : logs) {
			if (log.getTimeStamp().after(start)
					&& log.getTimeStamp().before(end)) {
				finded.add(log);
			}
		}
		System.out.println(finded.size());
		return new ResponseEntity<ArrayList<LogFirewall>>(finded, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(params = "srcIp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> getLogFirewallBySrcIp(
			@RequestParam(value = "srcIp") String ip) {
		ArrayList<LogFirewall> logs = logFirewallRepository
				.findLogFirewallBySrcIp(ip);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<LogFirewall>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<LogFirewall>>(logs, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(params = "dstIp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> getLogFirewallByDstIp(
			@RequestParam(value = "dstIp") String dstIp) {
		ArrayList<LogFirewall> logs = logFirewallRepository
				.findLogFirewallByDstIp(dstIp);
		if (logs.isEmpty()) {
			return new ResponseEntity<ArrayList<LogFirewall>>(
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<LogFirewall>>(logs, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(params = "protocol", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> getLogFirewallsByProtocol(
			@RequestParam String protocol) {
		ArrayList<LogFirewall> logs = new ArrayList<LogFirewall>();
		String p1 = protocol.toUpperCase();
		String p2 = "";
		String p3 = "";
		if (protocol.contains("|")) {
			String[] protocols = protocol.replaceAll("\\s", "").split("\\|");
			p1 = protocols[0];
			if (!protocols[1].equals("")) {
				p2 = protocols[1];
			}
			if (protocols.length > 2) {
				if (!protocols[2].equals("")) {
					p3 = protocols[2];
				}
			}
		}
			logs = logFirewallRepository.findLogFirewallByProtocol(p1, p2, p3);
			if (logs.isEmpty()) {
				return new ResponseEntity<ArrayList<LogFirewall>>(
						HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<ArrayList<LogFirewall>>(logs, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(params = "action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<LogFirewall>> getLogFirewallsByAction(
			@RequestParam(value = "action") String action) {
		ArrayList<LogFirewall> logs = new ArrayList<LogFirewall>();
		String p1 = action.toUpperCase();
		String p2 = "";
		String p3 = "";
		if (action.contains("|")) {
			String[] actions = action.replaceAll("\\s", "").split("\\|");
			p1 = actions[0];
			if (!actions[1].equals("")) {
				p2 = actions[1];
			}
			if (actions.length > 2) {
				if (!actions[2].equals("")) {
					p3 = actions[2];
				}
			}
		}
			logs = logFirewallRepository.findFirewallLogsByAction(p1, p2, p3);
			if (logs.isEmpty()) {
				return new ResponseEntity<ArrayList<LogFirewall>>(
						HttpStatus.NOT_FOUND);
			}
		return new ResponseEntity<ArrayList<LogFirewall>>(logs, HttpStatus.OK);

	}
}

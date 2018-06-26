/**
 * 
 */
package com.agent.controller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agent.domain.Agent;
import com.agent.domain.Level;
import com.agent.domain.OperatingSystemLog;
import com.sun.jna.platform.win32.Advapi32Util.EventLogIterator;
import com.sun.jna.platform.win32.Advapi32Util.EventLogRecord;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Vanja
 *
 */

@RestController
@RequestMapping("/OSlogs")
public class OperatingSystemLogController {
	public int sleepTime = 30000;
	// @Autowired
	// private OperatingSystemLogService osLogService;

	/*
	 * @CrossOrigin
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes =
	 * MediaType.APPLICATION_JSON_VALUE, produces =
	 * MediaType.APPLICATION_JSON_VALUE) public
	 * ResponseEntity<OperatingSystemLog> updateOperatingSystemLog(@RequestBody
	 * OperatingSystemLog osLog,
	 * 
	 * @PathVariable Long id) throws Exception { OperatingSystemLog exists =
	 * osLogService.findOne(id); if (exists == null) { return new
	 * ResponseEntity<OperatingSystemLog>(HttpStatus.NOT_FOUND); }
	 * OperatingSystemLog saved = null; if (osLog.getId() == id) { saved =
	 * osLogService.update(osLog); return new
	 * ResponseEntity<OperatingSystemLog>(saved, HttpStatus.OK); } return new
	 * ResponseEntity<OperatingSystemLog>(saved, HttpStatus.CONFLICT); }
	 */

	public void getOSlogs(String confFile, String sendTo) throws ParseException, IOException {
		
		EventLogIterator iter = new EventLogIterator("System");
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EventLogRecord record = iter.next();
			Long id = (long) record.getRecordNumber();
			String type = record.getRecord().EventType.toString();
			Level level = null;
			if (type.equals("1")) {
				level = Level.ERROR;
			} else if (type.equals("2")) {
				level = Level.WARNING;
			} else if (type.equals("4")) {
				level = Level.INFORMATION;
			}
			Date date = new Date(
					record.getRecord().TimeGenerated.longValue() * 1000);
			String source = record.getSource();
			int eventId = record.getStatusCode();

			OperatingSystemLog log = new OperatingSystemLog(null, level, date,
					source, eventId, new Agent());
			if(filterLog(log, confFile)){
				sendToCenter(log, sendTo);
			}
		}
	}

	public void sendToCenter(OperatingSystemLog log, String sendTo) throws IOException {
		RestTemplate restTemplate=new RestTemplate();
		sendTo=sendTo+"/create";
		
		HttpEntity<OperatingSystemLog> request= new HttpEntity<>(log);
		
		ResponseEntity<OperatingSystemLog> result = restTemplate.postForEntity(sendTo, request, OperatingSystemLog.class);
		System.out.println("Status code:" + result.getStatusCode());
	}
	
	public boolean filterLog(OperatingSystemLog osLog, String confFile){
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator + confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			JSONObject osObject = (JSONObject) jsonObject.get("os");
			JSONObject osFilterObject = (JSONObject) osObject.get("os_filter");
			Set<String> osFilterParams = osFilterObject.keySet();
			int numOfKey = 0;
			for (String param : osFilterParams) {
				if(param.equals("timeStamp")){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat.parse((String)osFilterObject.get(param));
						Date currentDate = new Date();
						if(osLog.getTimeStamp().after(date) && osLog.getTimeStamp().before(currentDate)){
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(param.equals("priority")){
					String[] priorityValues = osFilterObject.get(param).toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if(osLog.toString().toLowerCase().contains(pv)){
							numOfKey++;
						}
					}
				}else if(osLog.toString().toLowerCase().contains(osFilterObject.get(param).toString().toLowerCase())){
					numOfKey++;
				}
			}
			if(numOfKey == osFilterParams.size()){
				return true;
			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

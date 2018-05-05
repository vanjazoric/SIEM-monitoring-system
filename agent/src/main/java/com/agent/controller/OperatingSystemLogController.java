/**
 * 
 */
package com.agent.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.Level;
import com.agent.domain.OperatingSystemLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jna.platform.win32.Advapi32Util.EventLogIterator;
import com.sun.jna.platform.win32.Advapi32Util.EventLogRecord;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author Vanja
 *
 */

@RestController
@RequestMapping("/OSlogs")
public class OperatingSystemLogController {

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

	public void getOSlogs() throws ParseException, IOException {
		ArrayList<OperatingSystemLog> logs = new ArrayList<OperatingSystemLog>();
		EventLogIterator iter = new EventLogIterator("System");
		int counter = 0;
		while (counter < 15) {
			counter++;
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

			OperatingSystemLog log = new OperatingSystemLog(id, level, date,
					source, eventId, new Agent());
			logs.add(log);
		}
		sendToCenter(logs);
	}

	public void sendToCenter(ArrayList<OperatingSystemLog> logs)
			throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost("http://localhost:8888/OSlogs");
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			StringEntity postingString = new StringEntity(gson.toJson(logs));
			request.setEntity(postingString);
			request.setHeader("Content-type", "application/json");
			response = (CloseableHttpResponse) httpClient.execute(request);
		//	String json = EntityUtils.toString(response.getEntity());
		//	System.out.println(json);
		} catch (Exception ex) {
		} finally {
			response.close();
		}
	}
}
package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.ApplicationLog;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogPackage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/applicationLog")
public class ApplicationLogController implements Runnable{
	public int sleepTime = 1;
	public String sendTo;
	public String listenFrom;
	public String confFile;

	public ApplicationLogController() {
		super();
	}

	public ApplicationLogController(String sendTo, String listenFrom,
			String confFile) {
		super();
		this.sendTo = sendTo;
		this.listenFrom = listenFrom;
		this.confFile = confFile;
	}

	@CrossOrigin
	@RequestMapping(value = "/create/mediatorFinal/app", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> mediatorSendToCenter(
			@RequestBody LogPackage logPackage) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost(this.sendTo);
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			StringEntity postingString = new StringEntity(
					gson.toJson(logPackage));
			request.setEntity(postingString);
			request.setHeader("Content-type", "application/json");
			response = (CloseableHttpResponse) httpClient.execute(request);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			response.close();
		}
		return new ResponseEntity<LogFirewall>(HttpStatus.CREATED);

	}

	@CrossOrigin
	@RequestMapping(value = "/create/mediator/fw", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> mediatorAccept(
			@RequestBody LogPackage logPackage) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost(this.sendTo);
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			StringEntity postingString = new StringEntity(
					gson.toJson(logPackage));
			request.setEntity(postingString);
			request.setHeader("Content-type", "application/json");
			response = (CloseableHttpResponse) httpClient.execute(request);
		} catch (Exception ex) {

		} finally {
			response.close();
		}
		return new ResponseEntity<LogFirewall>(HttpStatus.CREATED);

	}

	public void loadApplicationLogs() throws IOException, ParseException {
		// TODO Auto-generated method stub
		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + listenFrom);
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			while ((line = in.readLine()) != null) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] data = line.split(";");
				Long logId = Long.parseLong(data[0]);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date timeStamp = sdf.parse(data[1]);
				int eventId = Integer.parseInt(data[2]);
				String priority = data[3];
				String application = data[4];
				Long messageId = Long.parseLong(data[5]);
				String message = data[6];
				Agent agent = new Agent();
				ApplicationLog al = new ApplicationLog(logId, timeStamp, agent,
						eventId, priority, application, messageId, message);
				System.out.println(al);
				if (filterLog(al, confFile)) {
					sendToCenter(al, sendTo);
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToCenter(ApplicationLog log, String sendTo)
			throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost(sendTo);
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			if (sendTo.contains("mediator")) {
				LogPackage logPackage = new LogPackage();
				logPackage.getApplicationLogs().add(log);
				StringEntity postingString = new StringEntity(
						gson.toJson(logPackage));
				request.setEntity(postingString);
				request.setHeader("Content-type", "application/json");
				response = (CloseableHttpResponse) httpClient.execute(request);
			} else {
				StringEntity postingString = new StringEntity(gson.toJson(log));
				request.setEntity(postingString);
				request.setHeader("Content-type", "application/json");
				response = (CloseableHttpResponse) httpClient.execute(request);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			response.close();
		}
	}

	public boolean filterLog(ApplicationLog appLog, String confFile) {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(
					path));
			JSONObject appObject = (JSONObject) jsonObject.get("ap");
			JSONObject appFilterObject = (JSONObject) appObject
					.get("ap_filter");
			Set<String> appFilterParams = appFilterObject.keySet();
			int numOfKey = 0;
			for (String param : appFilterParams) {
				if (param.equals("timeStamp")) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat
								.parse((String) appFilterObject.get(param));
						Date currentDate = new Date();
						if (appLog.getTimeStamp().after(date)
								&& appLog.getTimeStamp().before(currentDate)) {
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (param.equals("priority")) {
					String[] priorityValues = appFilterObject.get(param)
							.toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if (appLog.toString().toLowerCase().contains(pv)) {
							numOfKey++;
						}
					}
				} else if (appLog
						.toString()
						.toLowerCase()
						.contains(
								appFilterObject.get(param).toString()
										.toLowerCase())) {
					numOfKey++;
				}
			}
			if (numOfKey == appFilterParams.size()) {
				return true;
			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			this.loadApplicationLogs();
		}catch(Exception e){
			
		}
		
	}

}
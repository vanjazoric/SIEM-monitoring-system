package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
import com.agent.domain.LogFirewall;
import com.agent.domain.LogPackage;
import com.agent.domain.LogServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/logService")
public class LogServerController implements Runnable{
	public int sleepTime = 1;
	public String sendTo;
	public String listenFrom;
	public String confFile = "conf8080.json";

	public LogServerController() {
		super();
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		JSONObject jsonObject;
		JSONObject lsObject;

		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(path));
			Set<String> logTypes = jsonObject.keySet();
			for (String logType : logTypes) {
				if (logType.equals("ls")) {
					lsObject = (JSONObject) jsonObject.get("ls");
					this.sendTo = (String) lsObject.get("sendTo");
					this.confFile = confFile;
					this.listenFrom = (String) lsObject.get("listenFrom");
					Thread lscThread = new Thread(this);
					lscThread.start();
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public LogServerController(String sendTo, String listenFrom,
			String confFile) {
		super();
		this.sendTo = sendTo;
		this.listenFrom = listenFrom;
		this.confFile = confFile;
	}

	@CrossOrigin
	@RequestMapping(value = "/mediator/final", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
	@RequestMapping(value = "/mediator/forward", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

	public void readLogs() {
		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + listenFrom);
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			while ((line = in.readLine()) != null) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String[] tokens = line.split(" ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date timeStamp = new Date();
				try {
					timeStamp = sdf.parse(tokens[0] + " " + tokens[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				LogServer l = new LogServer(null, timeStamp, tokens[2],
						tokens[3], tokens[5], tokens[4], tokens[6],
						Integer.valueOf(tokens[7]), Integer.valueOf(tokens[8]),
						new Agent());
				if (filterLog(l, confFile)) {
					sendToCenter(l, sendTo);
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToCenter(LogServer log, String sendTo) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost(sendTo);
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
//			if (sendTo.contains("mediator")) {
//				LogPackage logPackage = new LogPackage();
//				logPackage.getLogsServer().add(log);
//				StringEntity postingString = new StringEntity(
//						gson.toJson(logPackage));
//				request.setEntity(postingString);
//				request.setHeader("Content-type", "application/json");
//				response = (CloseableHttpResponse) httpClient.execute(request);
//			} else {
				StringEntity postingString = new StringEntity(gson.toJson(log));
				request.setEntity(postingString);
				request.setHeader("Content-type", "application/json");

				response = (CloseableHttpResponse) httpClient.execute(request);
//			}
		} catch (Exception ex) {
		} finally {
			response.close();
		}
	}

	public boolean filterLog(LogServer ls_log, String confFile) {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(
					path));
			JSONObject lsObject = (JSONObject) jsonObject.get("ls");
			JSONObject lsFilterObject = (JSONObject) lsObject.get("ls_filter");
			Set<String> lsFilterParams = lsFilterObject.keySet();
			int numOfKey = 0;
			for (String param : lsFilterParams) {
				if (param.equals("timeStamp")) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat
								.parse((String) lsFilterObject.get(param));
						Date currentDate = new Date();
						if (ls_log.getTimeStamp().after(date)
								&& ls_log.getTimeStamp().before(currentDate)) {
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (param.equals("priority")) {
					String[] priorityValues = lsFilterObject.get(param)
							.toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if (ls_log.toString().toLowerCase().contains(pv)) {
							numOfKey++;
						}
					}
				} else if (ls_log
						.toString()
						.toLowerCase()
						.contains(
								lsFilterObject.get(param).toString()
										.toLowerCase())) {
					numOfKey++;
				}
			}
			if (numOfKey == lsFilterParams.size()) {
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
			this.readLogs();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

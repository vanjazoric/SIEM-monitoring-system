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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/firewallLog")
public class LogFirewallController implements Runnable {
	public int sleepTime = 1;
	public String sendTo;
	public String listenFrom;
	public String confFile = "conf8080.json";

	public LogFirewallController() {
		super();
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		JSONObject jsonObject;
		JSONObject fwObject;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(path));
			Set<String> logTypes = jsonObject.keySet();
			for (String logType : logTypes) {
				if (logType.equals("fw")) {
					fwObject = (JSONObject) jsonObject.get(logType);
					this.sendTo= (String) fwObject.get("sendTo");
					this.confFile = confFile;
					this.listenFrom = (String) fwObject.get("listenFrom");
					Thread lfcThread = new Thread(this);
					lfcThread.start();
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

	public LogFirewallController(String sendTo, String listenFrom,
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

	public void parse() {
		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + listenFrom);
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while ((line = in.readLine()) != null) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String tokens[] = line.trim().split(" ");
				Long id = null;
				Agent agent = new Agent();
				Date timeStamp = sdf.parse(tokens[0] + " " + tokens[1]);
				String action = tokens[2];
				String protocol = tokens[3];
				String srcIp = tokens[4];
				String dstIp = tokens[5];
				String srcPort = tokens[6];
				String dstPort = tokens[7];
				int size = Integer.parseInt(tokens[8]);
				String tcpflags = tokens[9];
				String tcpsync = tokens[10];
				LogFirewall lf = new LogFirewall(id, timeStamp, agent, action,
						protocol, srcIp, dstIp, srcPort, dstPort, size,
						tcpflags, tcpsync);
				if (filterLog(lf, confFile)) {
					sendToCenter(lf, sendTo);
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToCenter(LogFirewall log, String sendTo) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost(sendTo);
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			if (sendTo.contains("mediator")) {
				System.out.println(sendTo);
				LogPackage logPackage = new LogPackage();
				logPackage.getLogsFirewall().add(log);
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

	public boolean filterLog(LogFirewall fw_log, String confFile) {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(
					path));
			JSONObject fwObject = (JSONObject) jsonObject.get("fw");
			JSONObject fwFilterObject = (JSONObject) fwObject.get("fw_filter");
			Set<String> fwFilterParams = fwFilterObject.keySet();
			int numOfKey = 0;
			for (String param : fwFilterParams) {
				if (param.equals("timeStamp")) {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat
								.parse((String) fwFilterObject.get(param));
						Date currentDate = new Date();
						if (fw_log.getTimeStamp().after(date)
								&& fw_log.getTimeStamp().before(currentDate)) {
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (param.equals("priority")) {
					String[] priorityValues = fwFilterObject.get(param)
							.toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if (fw_log.toString().toLowerCase().contains(pv)) {
							numOfKey++;
						}
					}
				} else if (fw_log
						.toString()
						.toLowerCase()
						.contains(
								fwFilterObject.get(param).toString()
										.toLowerCase())) {
					numOfKey++;
				}
			}
			if (numOfKey == fwFilterParams.size()) {
				return true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

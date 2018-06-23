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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agent.domain.Agent;
import com.agent.domain.LogFirewall;

@RestController
@RequestMapping(value = "/firewallLog")
public class LogFirewallController {
	public int sleepTime = 30000;

	public void parse(String listenFrom, String confFile, String sendTo) {
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
				String id = null;
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
				if(filterLog(lf, confFile)){
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
		RestTemplate restTemplate=new RestTemplate();
		sendTo=sendTo+"/create";
		
		HttpEntity<LogFirewall> request= new HttpEntity<>(log);
		
		ResponseEntity<LogFirewall> result = restTemplate.postForEntity(sendTo, request, LogFirewall.class);
		System.out.println("Status code:" + result.getStatusCode());

	}
	
	public boolean filterLog(LogFirewall fw_log, String confFile){
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator + confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			JSONObject fwObject = (JSONObject) jsonObject.get("fw");
			JSONObject fwFilterObject = (JSONObject) fwObject.get("fw_filter");
			Set<String> fwFilterParams = fwFilterObject.keySet();
			int numOfKey = 0;
			for (String param : fwFilterParams) {
				if(param.equals("timeStamp")){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat.parse((String)fwFilterObject.get(param));
						Date currentDate = new Date();
						if(fw_log.getTimeStamp().after(date) && fw_log.getTimeStamp().before(currentDate)){
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(param.equals("priority")){
					String[] priorityValues = fwFilterObject.get(param).toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if(fw_log.toString().toLowerCase().contains(pv)){
							numOfKey++;
						}
					}
				}else if(fw_log.toString().toLowerCase().contains(fwFilterObject.get(param).toString().toLowerCase())){
					numOfKey++;
				}
			}
			if(numOfKey == fwFilterParams.size()){
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
}

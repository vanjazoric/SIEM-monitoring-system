package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agent.domain.Agent;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogServer;

@RestController
@RequestMapping(value = "/logService")
public class LogServerController {
	public int sleepTime = 30000; 
	
	@Autowired
	Agent agent;
	
	public LogServerController(Agent agent){
		this.agent=agent;
	}
	
	
	
	public void readLogs(String listenFrom, String confFile, String sendTo) {
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
						this.agent.getName());
				if(filterLog(l, confFile)){
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
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.set("agentName", this.agent.getName());
        headers.setContentType(MediaType.APPLICATION_JSON);
		sendTo=sendTo+"/create";
		
		HttpEntity<LogServer> request= new HttpEntity<>(log,headers);
		
		/*ResponseEntity<ApplicationLog>*/Object response = restTemplate.exchange(sendTo,HttpMethod.POST, request,Object.class);
		System.out.println("Status code:"/* + result.getStatusCode()*/);
	}
	
	public boolean filterLog(LogServer ls_log, String confFile){
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator + confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			JSONObject lsObject = (JSONObject) jsonObject.get("ls");
			JSONObject lsFilterObject = (JSONObject) lsObject.get("ls_filter");
			Set<String> lsFilterParams = lsFilterObject.keySet();
			int numOfKey = 0;
			for (String param : lsFilterParams) {
				if(param.equals("timeStamp")){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat.parse((String)lsFilterObject.get(param));
						Date currentDate = new Date();
						if(ls_log.getTimeStamp().after(date) && ls_log.getTimeStamp().before(currentDate)){
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(param.equals("priority")){
					String[] priorityValues = lsFilterObject.get(param).toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if(ls_log.toString().toLowerCase().contains(pv)){
							numOfKey++;
						}
					}
				}else if(ls_log.toString().toLowerCase().contains(lsFilterObject.get(param).toString().toLowerCase())){
					numOfKey++;
				}
			}
			if(numOfKey == lsFilterParams.size()){
				return true;
			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

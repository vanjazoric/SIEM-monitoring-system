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

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agent.domain.Agent;
import com.agent.domain.ApplicationLog;

@RestController
@RequestMapping(value = "/applicationLog")
public class ApplicationLogController {
	
	@Autowired
	Agent agent;
	
	public int sleepTime = 30000;
	
	public ApplicationLogController(Agent agent){
		this.agent=agent;
	}
	
	public void loadApplicationLogs(String listenFrom, String confFile, String sendTo) throws IOException, ParseException {
		// TODO Auto-generated method stub
		File relativeFile = new File(".." + File.separator + "scripts" + File.separator + listenFrom);
		try {
			BufferedReader in = new BufferedReader(new FileReader(relativeFile.getCanonicalPath()));
			String line;
			while ((line = in.readLine()) != null){
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] data = line.split(";");
			    String logId = data[0];
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date timeStamp = sdf.parse(data[1]);
			    int eventId = Integer.parseInt(data[2]);
			    String priority = data[3];
			    String application = data[4];
			    Long messageId = Long.parseLong(data[5]);
			    String message = data[6];
			    ApplicationLog al = new ApplicationLog(logId, timeStamp, this.agent.getName(), eventId, priority, application, messageId, message);
			    System.out.println(al);
			    if(filterLog(al, confFile)){
			    	sendToCenter(al, sendTo);
			    } 
			} 
			in.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendToCenter(ApplicationLog log, String sendTo) throws IOException {
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.set("agentName", this.agent.getName());
        headers.setContentType(MediaType.APPLICATION_JSON);
		//sendTo=sendTo+"/create";
		
		HttpEntity<ApplicationLog> request= new HttpEntity<>(log,headers);
		
		/*ResponseEntity<ApplicationLog>*/Object response = restTemplate.exchange(sendTo,HttpMethod.POST, request,Object.class);
		System.out.println("Status code:"/* + result.getStatusCode()*/);
	}
	
	public boolean filterLog(ApplicationLog appLog, String confFile){
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator + confFile;
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			JSONObject appObject = (JSONObject) jsonObject.get("ap");
			JSONObject appFilterObject = (JSONObject) appObject.get("ap_filter");
			Set<String> appFilterParams = appFilterObject.keySet();
			int numOfKey = 0;
			for (String param : appFilterParams) {
				if(param.equals("timeStamp")){
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = simpleDateFormat.parse((String)appFilterObject.get(param));
						Date currentDate = new Date();
						if(appLog.getTimeStamp().after(date) && appLog.getTimeStamp().before(currentDate)){
							numOfKey++;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(param.equals("priority")){
					String[] priorityValues = appFilterObject.get(param).toString().toLowerCase().split("\\|");
					for (String pv : priorityValues) {
						if(appLog.toString().toLowerCase().contains(pv)){
							numOfKey++;
						}
					}
				}else if(appLog.toString().toLowerCase().contains(appFilterObject.get(param).toString().toLowerCase())){
					numOfKey++;
				}
			}
			if(numOfKey == appFilterParams.size()){
				return true;
			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
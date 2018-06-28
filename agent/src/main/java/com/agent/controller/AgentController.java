package com.agent.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogPackage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {
	
	public String sendTo;
	public String listenFrom;
	public String confFile = "conf.json";
	public String source;
	
	public AgentController(){
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator + confFile;
		JSONObject jsonObject;
		
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(path));
			Set<String> logTypes = jsonObject.keySet();
			for (String logType : logTypes) {
				if(logType.equals("ap")){
					JSONObject appObject = (JSONObject) jsonObject.get(logType);
					this.sendTo = (String)appObject.get("sendTo");
					this.confFile = confFile;
					String tokens[] = this.sendTo.trim().split("/");
					String source = tokens[0] + tokens[1] + tokens[2];
					Agent agent = new Agent();
					agent.setId(source);
					agent.setConfFile(confFile);
					agent.setName(source);
					agent.setSendTo(sendTo);
					sendToCenter(agent);
				}else if(logType.equals("fw")){
					JSONObject appObject = (JSONObject) jsonObject.get(logType);
					this.sendTo = (String)appObject.get("sendTo");
					this.confFile = confFile;
					String tokens[] = this.sendTo.trim().split("/");
					String source = tokens[0] + tokens[1] + tokens[2];
					Agent agent = new Agent();
					agent.setId(source);
					agent.setConfFile(confFile);
					agent.setName(source);
					agent.setSendTo(sendTo);
					sendToCenter(agent);
				}else if(logType.equals("ls")){
					JSONObject appObject = (JSONObject) jsonObject.get(logType);
					this.sendTo = (String)appObject.get("sendTo");
					this.confFile = confFile;
					String tokens[] = this.sendTo.trim().split("/");
					String source = tokens[0] + tokens[1] + tokens[2];
					Agent agent = new Agent();
					agent.setId(source);
					agent.setConfFile(confFile);
					agent.setName(source);
					agent.setSendTo(sendTo);
					sendToCenter(agent);
				}else{
					JSONObject appObject = (JSONObject) jsonObject.get(logType);
					this.sendTo = (String)appObject.get("sendTo");
					this.confFile = confFile;
					String tokens[] = this.sendTo.trim().split("/");
					String source = tokens[0] + tokens[1] + tokens[2];
					Agent agent = new Agent();
					agent.setId(source);
					agent.setConfFile(confFile);
					agent.setName(source);
					agent.setSendTo(sendTo);
					sendToCenter(agent);
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
	
	public void sendToCenter(Agent agent) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost("http://localhost:8888/agent/create");
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
				StringEntity postingString = new StringEntity(gson.toJson(agent));
				request.setEntity(postingString);
				request.setHeader("Content-type", "application/json");
				response = (CloseableHttpResponse) httpClient.execute(request);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			response.close();
		}
	}
}

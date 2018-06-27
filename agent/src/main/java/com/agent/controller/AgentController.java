package com.agent.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {
	
	//@Autowired
	//ApplicationLogController alc;
	
//	public AgentController(){
//		this.alc = new ApplicationLogController();
//	}

	public void run(String confFile) throws ParseException, IOException,
			NumberFormatException, InterruptedException {
//		JSONParser parser = new JSONParser();
//		String path = ".." + File.separator + "scripts" + File.separator
//				+ confFile;
//		JSONObject jsonObject;
//		JSONObject appObject;
//		JSONObject fwObject;
//		JSONObject lsObject;
//		JSONObject osObject;
//		OperatingSystemLogController oslc = new OperatingSystemLogController();
//
//		try {
//			jsonObject = (JSONObject) parser.parse(new FileReader(path));
//			Set<String> logTypes = jsonObject.keySet();
//			for (String logType : logTypes) {
//				if (logType.equals("os")){
//					osObject = (JSONObject) jsonObject.get("os");
//					oslc.sendTo = (String) osObject.get("sendTo");
//				}
//			}
//			for (String logType : logTypes) {
//				if (logType.equals("os")) {
//					osObject = (JSONObject) jsonObject.get("os");
//					oslc.confFile = confFile;
//					Thread oslcThread = new Thread(oslc);
//					oslcThread.start();
//					//oslc.getOSlogs();
//				}
//			}
//		} catch (org.json.simple.parser.ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

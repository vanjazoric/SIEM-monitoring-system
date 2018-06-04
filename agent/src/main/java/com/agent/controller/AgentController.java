package com.agent.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {

	public void run(String confFile) throws ParseException, IOException, NumberFormatException, InterruptedException{
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator + confFile;
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(path));
			Set<String> logTypes = jsonObject.keySet();
			for (String logType : logTypes) {
				if(logType.equals("ap")){
					JSONObject appObject = (JSONObject) jsonObject.get(logType);
					ApplicationLogController alc = new ApplicationLogController();
					alc.loadApplicationLogs((String)appObject.get("listenFrom"), confFile, (String)appObject.get("sendTo"));
				}else if(logType.equals("fw")){
					JSONObject fwObject = (JSONObject) jsonObject.get(logType);
					LogFirewallController lfc = new LogFirewallController();
					lfc.parse((String)fwObject.get("listenFrom"), confFile, (String)fwObject.get("sendTo"));
				}else if(logType.equals("ls")){
					JSONObject lsObject = (JSONObject) jsonObject.get("ls");
					LogServerController lsc = new LogServerController();
					lsc.readLogs((String)lsObject.get("listenFrom"), confFile, (String)lsObject.get("sendTo"));
				}else{
					JSONObject osObject = (JSONObject) jsonObject.get("os");
					OperatingSystemLogController oslc = new OperatingSystemLogController();
					oslc.getOSlogs(confFile, (String)osObject.get("sendTo"));
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

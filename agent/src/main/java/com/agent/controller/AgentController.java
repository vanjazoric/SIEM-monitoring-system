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

	public void run(String confFile) throws ParseException, IOException,
			NumberFormatException, InterruptedException {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		JSONObject jsonObject;
		JSONObject appObject;
		JSONObject fwObject;
		JSONObject lsObject;
		JSONObject osObject;
		ApplicationLogController alc = new ApplicationLogController();
		LogFirewallController lfc = new LogFirewallController();
		LogServerController lsc = new LogServerController();
		OperatingSystemLogController oslc = new OperatingSystemLogController();

		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(path));
			Set<String> logTypes = jsonObject.keySet();
			for (String logType : logTypes) {
				if (logType.equals("ap")) {
					appObject = (JSONObject) jsonObject.get(logType);
					alc.sendTo = (String)appObject.get("sendTo");
				} else if (logType.equals("fw")) {
					fwObject = (JSONObject) jsonObject.get(logType);
					lfc.sendTo= (String) fwObject.get("sendTo");
				} else if (logType.equals("ls")) {
					lsObject = (JSONObject) jsonObject.get("ls");
					lsc.sendTo = (String) lsObject.get("sendTo");
				} else {
					osObject = (JSONObject) jsonObject.get("os");
					oslc.sendTo = (String) osObject.get("sendTo");
				}
			}
			for (String logType : logTypes) {
				if (logType.equals("ap")) {
					appObject = (JSONObject) jsonObject.get(logType);
					alc.confFile = confFile;
					alc.listenFrom = (String) appObject.get("listenFrom");
					Thread alcThread = new Thread(alc);
					alcThread.start();
					//alc.loadApplicationLogs();
				} else if (logType.equals("fw")) {
					fwObject = (JSONObject) jsonObject.get(logType);
					lfc.confFile = confFile;
					lfc.listenFrom = (String) fwObject.get("listenFrom");
					Thread lfcThread = new Thread(lfc);
					lfcThread.start();
					//lfc.parse();
				} else if (logType.equals("ls")) {
					lsObject = (JSONObject) jsonObject.get("ls");
					lsc.confFile = confFile;
					lsc.listenFrom = (String) lsObject.get("listenFrom");
					Thread lscThread = new Thread(lsc);
					lscThread.start();
					//lsc.readLogs();
				} else {
					osObject = (JSONObject) jsonObject.get("os");
					oslc.confFile = confFile;
					Thread oslcThread = new Thread(oslc);
					oslcThread.start();
					//oslc.getOSlogs();
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

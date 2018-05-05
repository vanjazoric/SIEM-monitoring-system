package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.LogFirewall;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {

	private ArrayList<String> logTypes;
	private ArrayList<String> listenFroms;
	private ArrayList<String> sendTos;

	public void loadConfiguration(String confFile) {

		this.logTypes = new ArrayList<String>();
		this.listenFroms = new ArrayList<String>();
		this.sendTos = new ArrayList<String>();
		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + confFile);
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			while ((line = in.readLine()) != null) {
				String logType;
				String listenFrom;
				String sendTo;
				String tokens[] = line.trim().split(" ");
				logType = tokens[0];
				listenFrom = tokens[1];
				sendTo = tokens[2];
				this.listenFroms.add(listenFrom);
				this.logTypes.add(logType);
				this.sendTos.add(sendTo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run(String confFile) {
		loadConfiguration(confFile);
		for (int i = 0; i < this.logTypes.size(); i++) {
			if (this.logTypes.get(i).equals("fw")) {
				LogFirewallController lfc = new LogFirewallController();
				lfc.parse(this.listenFroms.get(i), this.sendTos.get(i));
			} else if (this.logTypes.get(i).equals("ls")) {
				LogServerController lsc = new LogServerController();
				lsc.readLogs(this.listenFroms.get(i), this.sendTos.get(i));
			} else if(this.logTypes.get(i).equals("appl")){
				ApplicationLogController alc = new ApplicationLogController();
				try {
					alc.loadApplicationLogs(this.listenFroms.get(i), this.sendTos.get(i));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (this.logTypes.get(i).equals("os")) {
				OperatingSystemLogController osl = new OperatingSystemLogController();
				try {
					osl.getOSlogs(this.sendTos.get(i));
				} catch (ParseException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//continue;
				}
			} 
		}
	}

}

package com.agent;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.controller.AgentController;
import com.agent.controller.LogServerController;
import com.agent.controller.ApplicationLogController;
import com.agent.controller.LogFirewallController;
import com.agent.controller.OperatingSystemLogController;

@SpringBootApplication
public class AgentApplication {

	public static void main(String[] args) throws ParseException, IOException {
//		SpringApplication.run(AgentApplication.class, args);
//		LogServerController c = new LogServerController();
//		c.readLogs();
//		OperatingSystemLogController osc = new OperatingSystemLogController();
//		osc.getOSlogs();
//		ApplicationLogController alc = new ApplicationLogController();
//		alc.loadApplicationLogs("logs.txt");
//		LogFirewallController lfc = new LogFirewallController();
//		lfc.parse();
		AgentController ac = new AgentController();
		ac.run("conf.json");

	}
}

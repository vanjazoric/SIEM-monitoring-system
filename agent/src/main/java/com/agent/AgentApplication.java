package com.agent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.controller.ApplicationLogController;
import com.agent.controller.OperatingSystemLogController;
import com.agent.domain.ApplicationLog;

@SpringBootApplication
public class AgentApplication {

	public static void main(String[] args) throws ParseException, IOException {
		SpringApplication.run(AgentApplication.class, args);
		OperatingSystemLogController c = new OperatingSystemLogController();
		c.getOSlogs();
		ApplicationLogController alc = new ApplicationLogController();
		alc.loadApplicationLogs("logs.txt");

	}
}

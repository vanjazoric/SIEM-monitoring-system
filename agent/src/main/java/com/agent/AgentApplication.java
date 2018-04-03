package com.agent;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.controller.LogServerController;
import com.agent.controller.OperatingSystemLogController;

@SpringBootApplication
public class AgentApplication {

	public static void main(String[] args) throws ParseException, IOException {
		SpringApplication.run(AgentApplication.class, args);
		//OperatingSystemLogController c = new OperatingSystemLogController();
		//c.getOSlogs();
		LogServerController c=new LogServerController();
		c.readLogs();

	}
}

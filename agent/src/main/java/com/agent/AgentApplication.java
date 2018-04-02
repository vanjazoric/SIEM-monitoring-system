package com.agent;

import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.controller.OperatingSystemLogController;

@SpringBootApplication
public class AgentApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(AgentApplication.class, args);
		OperatingSystemLogController c = new OperatingSystemLogController();
		c.getOSlogs();

	}
}

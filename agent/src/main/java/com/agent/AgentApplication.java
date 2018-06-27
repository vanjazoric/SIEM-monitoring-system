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

	public static void main(String[] args) throws ParseException, IOException, NumberFormatException, InterruptedException{
		SpringApplication.run(AgentApplication.class, args);
	}
}

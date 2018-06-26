package com.agent;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.agent.controller.AgentController;
import com.agent.controller.LogServerController;
import com.agent.controller.ApplicationLogController;
import com.agent.controller.LogFirewallController;
import com.agent.controller.OperatingSystemLogController;

@SpringBootApplication
public class AgentApplication {

	public static void main(String[] args) throws ParseException, IOException, NumberFormatException, InterruptedException{
		//SpringApplication.run(AgentApplication.class, args);
		AgentController ac = new AgentController();
		ac.run("conf.json");
	}
	
	/*@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws ParseException, IOException, NumberFormatException, InterruptedException {
		AgentController ac = new AgentController();
		ac.run("conf.json");
	}*/
}

package com.agent;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.agent.controller.AgentController;


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

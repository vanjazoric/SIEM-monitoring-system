package com.center;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.center.controller.AlarmController;

@SpringBootApplication
public class CenterApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CenterApplication.class, args);
		System.out.println("Application started!");
		AlarmController ac = ctx.getBean(AlarmController.class);
		//ac.triggerAlarm();
	}	
	
	
}

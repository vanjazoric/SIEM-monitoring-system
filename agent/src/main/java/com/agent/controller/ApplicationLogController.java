package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.ApplicationLog;

@RestController
@RequestMapping(value = "/applicationLog")
public class ApplicationLogController {
	
	public ArrayList<ApplicationLog> loadApplicationLogs(String filename) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<ApplicationLog> logs = new ArrayList<ApplicationLog>();
		File relativeFile = new File(".."+File.separator+"scripts"+File.separator+filename);
		try {
			BufferedReader in = new BufferedReader(new FileReader(relativeFile.getCanonicalPath()));
		    String line;
		    int counter = 1;
			while (((line = in.readLine()) != null) && counter<=15){
		    	String[] data = line.split(";");
		    	Long logId = Long.parseLong(data[0]);
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	Date timeStamp = sdf.parse(data[1]);
		    	int eventId = Integer.parseInt(data[2]);
		    	String priority = data[3];
		    	String application = data[4];
		    	Long messageId = Long.parseLong(data[5]);
		    	String message = data[6];
		    	Agent agent = new Agent();
		    	ApplicationLog al = new ApplicationLog(logId, timeStamp, agent, eventId, priority, application, messageId, message);
		    	logs.add(al);
		    	counter+=1;
			}
			System.out.println(logs.size());
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return logs;
	}

}

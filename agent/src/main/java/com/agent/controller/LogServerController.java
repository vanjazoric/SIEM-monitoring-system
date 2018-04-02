package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.LogServer;
import com.agent.service.LogServerService;

@RestController
@RequestMapping(value = "/logService")
public class LogServerController {

	@Autowired
	LogServerService logServerService;
	
	public void readLogs(){
		List<LogServer> logs=new ArrayList<>();
		File relativeFile = new File(".."+File.separator+"scripts"+File.separator+"logserver.txt");
		try {
			BufferedReader in=new BufferedReader(new FileReader(relativeFile.getCanonicalPath()));
			String line;
			while((line=in.readLine())!=null){
				String[] tokens=line.split(" ");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date timeStamp=new Date();
		    	try {
					timeStamp = sdf.parse(tokens[0]+" "+tokens[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		        LogServer l=new LogServer(timeStamp, tokens[2], tokens[3], tokens[5], tokens[4], tokens[6], Integer.valueOf(tokens[7]), Integer.valueOf(tokens[8]));
			    logs.add(l);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

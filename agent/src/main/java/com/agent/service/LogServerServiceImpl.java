package com.agent.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.agent.domain.LogServer;

@Service
public class LogServerServiceImpl implements LogServerService {

	@Override
	public List<LogServer> loadServerLogs(){
		try {
			BufferedReader in=new BufferedReader(new FileReader(""));
			String line;
			while((line=in.readLine())!=null){
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}

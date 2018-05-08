package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.ApplicationLog;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogServer;
import com.agent.domain.OperatingSystemLog;


@RestController
@RequestMapping(value = "/agent")
public class AgentController {

	private ArrayList<String> logTypes;
	private ArrayList<String> listenFroms;
	private ArrayList<String> sendTos;
	private HashMap<String, HashMap<String, String>> filter_params;

	public void loadConfiguration(String confFile) {

		this.logTypes = new ArrayList<String>();
		this.listenFroms = new ArrayList<String>();
		this.sendTos = new ArrayList<String>();
		this.filter_params = new HashMap<String, HashMap<String, String>>();
		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + confFile);
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			String regular_expression = "";
			while ((line = in.readLine()) != null) {
				if(line.contains("http")){
					String logType;
					String listenFrom;
					String sendTo;
					String tokens[] = line.trim().split(" ");
					logType = tokens[0];
					listenFrom = tokens[1];
					sendTo = tokens[2];
					this.listenFroms.add(listenFrom);
					this.logTypes.add(logType);
					this.sendTos.add(sendTo);
				}else{
					regular_expression += line.trim();
				}
			}
			int start = 0; 
			int end = 0; 
			String params = "";
			String log = "";
			for(int i = 0; i < regular_expression.length(); i++) { 
				if(regular_expression.charAt(i) == '_'){
					log = regular_expression.substring(i-2, i);
				}
			    if(regular_expression.charAt(i) == '{'){ 
			       start = i;
			    }
			    else if(regular_expression.charAt(i) == '}'){
			       end = i;
			       params = regular_expression.substring(start+1, end);
			       HashMap<String, String> param_values = new HashMap<String, String>();
			       String[] data = params.split(",");
			       for (String d : data) {
					String key = d.split(" : ")[0];
					key = key.substring(1, key.length()-1);
					String value = d.split(" : ")[1];
					value = value.substring(1, value.length()-1);
					param_values.put(key, value);
			       }
			       filter_params.put(log, param_values);
			    }
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run(String confFile) throws ParseException, IOException {
		loadConfiguration(confFile);
		for (int i = 0; i < this.logTypes.size(); i++) {
			if (this.logTypes.get(i).equals("ap") && filter_params.containsKey(logTypes.get(i))) {
				ApplicationLogController alc = new ApplicationLogController();
				try {
					ArrayList<ApplicationLog> logs = alc.loadApplicationLogs(this.listenFroms.get(i));
					ArrayList<ApplicationLog> filteredApplicationLogs = new ArrayList<ApplicationLog>();
					for(int j = 0; j < logs.size(); j++){
						int numberOfKey = 0;
						for (String key : filter_params.get("ap").keySet()) {
							if(key.equals("timeStamp")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = simpleDateFormat.parse(filter_params.get(logTypes.get(i)).get(key));
								Date currentDate = new Date();
								if(logs.get(j).getTimeStamp().after(date) && logs.get(j).getTimeStamp().before(currentDate)){
									numberOfKey++;
								}
							}else if(logs.get(j).toString().toLowerCase().contains(filter_params.get(logTypes.get(i)).get(key).toLowerCase())){
								numberOfKey++;
							}
						}
						if(numberOfKey == filter_params.get("ap").keySet().size()){
							filteredApplicationLogs.add(logs.get(j));
						}
					}
					alc.sendToCenter(filteredApplicationLogs, this.sendTos.get(i));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(this.logTypes.get(i).equals("fw") && filter_params.containsKey(logTypes.get(i))){
				LogFirewallController lfc = new LogFirewallController();
				ArrayList<LogFirewall> logs = lfc.parse(this.listenFroms.get(i));
				ArrayList<LogFirewall> filteredFirewallLogs = new ArrayList<LogFirewall>();
				for(int j = 0; j < logs.size(); j++){
					int numberOfKey = 0;
					for (String key : filter_params.get(logTypes.get(i)).keySet()) {
						if(key.equals("timeStamp")){
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = simpleDateFormat.parse(filter_params.get(logTypes.get(i)).get(key));
							Date currentDate = new Date();
							if(logs.get(j).getTimeStamp().after(date) && logs.get(j).getTimeStamp().before(currentDate)){
								numberOfKey++;
							}
						}else if(logs.get(j).toString().toLowerCase().contains(filter_params.get(logTypes.get(i)).get(key).toLowerCase())){
							numberOfKey++;
						}
					}
					if(numberOfKey == filter_params.get(logTypes.get(i)).size()){
						filteredFirewallLogs.add(logs.get(j));
					}
				}
				lfc.sendToCenter(filteredFirewallLogs, sendTos.get(i));
			}else if(this.logTypes.get(i).equals("ls") && filter_params.containsKey(logTypes.get(i))){
				LogServerController lsc = new LogServerController();
				ArrayList<LogServer> logs = lsc.readLogs(this.listenFroms.get(i));
				ArrayList<LogServer> filteredServerLogs = new ArrayList<LogServer>();
				for(int j = 0; j < logs.size(); j++){
					int numberOfKey = 0;
					for (String key : filter_params.get(logTypes.get(i)).keySet()) {
						if(key.equals("timeStamp")){
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = simpleDateFormat.parse(filter_params.get(logTypes.get(i)).get(key));
							Date currentDate = new Date();
							if(logs.get(j).getTimeStamp().after(date) && logs.get(j).getTimeStamp().before(currentDate)){
								numberOfKey++;
							}
						}else if(logs.get(j).toString().toLowerCase().contains(filter_params.get(logTypes.get(i)).get(key).toLowerCase())){
							numberOfKey++;
						}
					}
					if(numberOfKey == filter_params.get(logTypes.get(i)).size()){
						filteredServerLogs.add(logs.get(j));
					}
				}
				lsc.sendToCenter(filteredServerLogs, sendTos.get(i));
			}else{
				OperatingSystemLogController oslc = new OperatingSystemLogController();
				ArrayList<OperatingSystemLog> logs = oslc.getOSlogs();
				ArrayList<OperatingSystemLog> filteredOperatingSystemLogs = new ArrayList<OperatingSystemLog>();
				for(int j = 0; j < logs.size(); j++){
					System.out.println( "-----");
					int numberOfKey = 0;
					for (String key : filter_params.get(logTypes.get(i)).keySet()) {
						if(key.equals("timeStamp")){
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = simpleDateFormat.parse(filter_params.get(logTypes.get(i)).get(key));
							Date currentDate = new Date();
							if(logs.get(j).getTimeStamp().after(date) && logs.get(j).getTimeStamp().before(currentDate)){
								numberOfKey++;
							}
						}else if(logs.get(j).toString().toLowerCase().contains(filter_params.get(logTypes.get(i)).get(key).toLowerCase())){
							numberOfKey++;
						}
					}
					if(numberOfKey == filter_params.get(logTypes.get(i)).size()){
						filteredOperatingSystemLogs.add(logs.get(j));
					}
				}
				System.out.println(filteredOperatingSystemLogs.size());
				oslc.sendToCenter(filteredOperatingSystemLogs, sendTos.get(i));
			}
		}
		
	}

}

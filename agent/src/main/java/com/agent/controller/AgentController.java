package com.agent.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.ApplicationLog;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogServer;
import com.agent.domain.OperatingSystemLog;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {
	public Set<String> logTypes;
	public Set<String> apFilterParams;
	public Set<String> fwFilterParams;
	public Set<String> lsFilterParams;
	public Set<String> osFilterParams;
	public ArrayList<ApplicationLog> applicationLogs = new ArrayList<ApplicationLog>();
	public ArrayList<LogFirewall> firewallLogs = new ArrayList<LogFirewall>();
	public ArrayList<LogServer> logServerLogs = new ArrayList<LogServer>();
	public ArrayList<OperatingSystemLog> osLogs = new ArrayList<OperatingSystemLog>();

	public void run(String confFile) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		try {
			String path = ".." + File.separator + "scripts" + File.separator + confFile;
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			logTypes = jsonObject.keySet();
			for (String log : logTypes) {
				if(log.equals("ap")){
					JSONObject apObject = (JSONObject) jsonObject.get("ap");
					JSONObject ap_filter = (JSONObject) apObject.get("ap_filter");
					apFilterParams = ap_filter.keySet();
					ApplicationLogController alc = new ApplicationLogController();
					ArrayList<ApplicationLog> ap_logs = alc.loadApplicationLogs((String)apObject.get("listenFrom"));
					for (ApplicationLog ap_log : ap_logs) {
						int numOfKey = 0;
						for (String param : apFilterParams) {
							if(param.equals("timeStamp")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = simpleDateFormat.parse((String)ap_filter.get(param));
								Date currentDate = new Date();
								if(ap_log.getTimeStamp().after(date) && ap_log.getTimeStamp().before(currentDate)){
									numOfKey++;
								}
								
							}else if(param.equals("priority")){
								String data = (String)ap_filter.get("priority");
								String[] priorities = data.toLowerCase().split("\\|");
								for (String p : priorities) {
									if(p.equals("error")){
										if(ap_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else if(p.equals("information")){
										if(ap_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else{
										numOfKey++;
									}
								}
							}else if(ap_log.toString().toLowerCase().contains(ap_filter.get(param).toString().toLowerCase())){
								numOfKey++;
							}
							
						}
						if(numOfKey == apFilterParams.size()){
							applicationLogs.add(ap_log);
						}
					}
					alc.sendToCenter(applicationLogs, (String)apObject.get("sendTo"));
				}else if(log.equals("fw")){
					JSONObject fwObject = (JSONObject) jsonObject.get("fw");
					JSONObject fw_filter = (JSONObject) fwObject.get("fw_filter");
					fwFilterParams = fw_filter.keySet();
					LogFirewallController lfc = new LogFirewallController();
					ArrayList<LogFirewall> firewall_logs = lfc.parse((String)fwObject.get("listenFrom"));
					for (LogFirewall fw_log : firewall_logs) {
						int numOfKey = 0;
						for (String param : fwFilterParams) {
							if(param.equals("timeStamp")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = simpleDateFormat.parse((String)fw_filter.get(param));
								Date currentDate = new Date();
								if(fw_log.getTimeStamp().after(date) && fw_log.getTimeStamp().before(currentDate)){
									numOfKey++;
								}
								
							}else if(param.equals("priority")){
								String data = (String)fw_filter.get("priority");
								String[] priorities = data.toLowerCase().split("\\|");
								for (String p : priorities) {
									if(p.equals("error")){
										if(fw_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else if(p.equals("information")){
										if(fw_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else{
										numOfKey++;
									}
								}
							}else if(fw_log.toString().toLowerCase().contains(fw_filter.get(param).toString().toLowerCase())){
								numOfKey++;
							}
							
						}
						if(numOfKey == fwFilterParams.size()){
							firewallLogs.add(fw_log);
						}
					}
					lfc.sendToCenter(firewallLogs, (String)fwObject.get("sendTo"));
				}else if(log.equals("ls")){
					JSONObject lsObject = (JSONObject) jsonObject.get("ls");
					JSONObject ls_filter = (JSONObject) lsObject.get("ls_filter");
					lsFilterParams = ls_filter.keySet();
					LogServerController lsc = new LogServerController();
					ArrayList<LogServer> server_logs = lsc.readLogs((String)lsObject.get("listenFrom"));
					for (LogServer ls_log : server_logs) {
						int numOfKey = 0;
						for (String param : lsFilterParams) {
							if(param.equals("timeStamp")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = simpleDateFormat.parse((String)ls_filter.get(param));
								Date currentDate = new Date();
								if(ls_log.getTimeStamp().after(date) && ls_log.getTimeStamp().before(currentDate)){
									numOfKey++;
								}
								
							}else if(param.equals("priority")){
								String data = (String)ls_filter.get("priority");
								String[] priorities = data.toLowerCase().split("\\|");
								for (String p : priorities) {
									if(p.equals("error")){
										if(ls_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else if(p.equals("information")){
										if(ls_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else{
										numOfKey++;
									}
								}
							}else if(ls_log.toString().toLowerCase().contains(ls_filter.get(param).toString().toLowerCase())){
								numOfKey++;
							}
							
						}
						if(numOfKey == lsFilterParams.size()){
							logServerLogs.add(ls_log);
						}
					}
					lsc.sendToCenter(logServerLogs, (String)lsObject.get("sendTo"));
				}else{
					JSONObject osObject = (JSONObject) jsonObject.get("os");
					JSONObject os_filter = (JSONObject) osObject.get("os_filter");
					osFilterParams = os_filter.keySet();
					OperatingSystemLogController oslc = new OperatingSystemLogController();
					ArrayList<OperatingSystemLog> os_logs = oslc.getOSlogs();
					for (OperatingSystemLog os_log : os_logs) {
						int numOfKey = 0;
						for (String param : osFilterParams) {
							if(param.equals("timeStamp")){
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = simpleDateFormat.parse((String)os_filter.get(param));
								Date currentDate = new Date();
								if(os_log.getTimeStamp().after(date) && os_log.getTimeStamp().before(currentDate)){
									numOfKey++;
								}
								
							}else if(param.equals("priority")){
								String data = (String)os_filter.get("priority");
								String[] priorities = data.toLowerCase().split("\\|");
								for (String p : priorities) {
									if(p.equals("error")){
										if(os_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else if(p.equals("information")){
										if(os_log.toString().toLowerCase().contains(p)){
											numOfKey++;
										}
									}else{
										numOfKey++;
									}
								}
							}else if(os_log.toString().toLowerCase().contains(os_filter.get(param).toString().toLowerCase())){
								numOfKey++;
							}
							
						}
						if(numOfKey == osFilterParams.size()){
							osLogs.add(os_log);
						}
					}
					oslc.sendToCenter(osLogs, (String)osObject.get("sendTo"));
				}
			}
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

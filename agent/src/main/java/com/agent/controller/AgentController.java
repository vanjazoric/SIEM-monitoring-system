package com.agent.controller;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.agent.DTO.AgentDTO;
import com.agent.domain.Agent;
import com.agent.domain.ApplicationLog;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogServer;
import com.agent.domain.OperatingSystemLog;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AgentController {
	
	final String CONF_FILE_NAME = "conf.json";

	@Autowired
	Agent agent;

	@Autowired
	ApplicationLogController applicationLogController;
	
	@Autowired
	LogFirewallController logFirewallController;
	
	@Autowired
	LogServerController logServerController;
	
	@Autowired
	OperatingSystemLogController osLogController;

	public AgentController(Agent agent,
			ApplicationLogController applicationLogController,
			LogFirewallController logFirewallController,
			LogServerController logServerController,
			OperatingSystemLogController osLogController) {
		this.agent = agent;
		this.applicationLogController = applicationLogController;
		this.logFirewallController = logFirewallController;
		this.logServerController =logServerController;
		this.osLogController = osLogController;
		System.out.println("Agent started!");
		try {
			run(CONF_FILE_NAME);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(String confFile) throws ParseException, IOException,
			NumberFormatException, InterruptedException {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ confFile;
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) parser.parse(new FileReader(path));

			JSONObject parentObject = (JSONObject) jsonObject.get("parent");
			String ip = (String) jsonObject.get("ip");
			this.agent.setIp(ip);
			String port = (String) jsonObject.get("port");
			this.agent.setPort(port);
			String sendTo = "https://" + (String) parentObject.get("ip") + ":"
					+ (String) parentObject.get("port");
			String agentName = (String) jsonObject.get("agentName");
			this.agent.setName(agentName);
			this.agent.setParentIp((String) parentObject.get("ip"));
			String parentName = (String) parentObject.get("name");
			this.agent.setParentName(parentName);
			System.out.println("\n\n\nOVO JE AGENTOV PARENT IP "
					+ agent.getParentIp() + "\n\n\n");
			this.agent.setParentPort((String) parentObject.get("port"));
			if(jsonObject.containsKey("ap")){
				JSONObject appObject = (JSONObject) jsonObject.get("ap");
				String appFileName = (String) appObject.get("listenFrom");
				this.agent.setApLogsDest(appFileName);
			}
			if(jsonObject.containsKey("fw")){
				JSONObject fwObject = (JSONObject) jsonObject.get("fw");
				String fwFileName = (String) fwObject.get("listenFrom");
				this.agent.setFwLogsDest(fwFileName);
			}
			if(jsonObject.containsKey("ls")){
				JSONObject lsObject = (JSONObject) jsonObject.get("ls");
				String lsFileName = (String) lsObject.get("listenFrom");
				this.agent.setServerLogsDest(lsFileName);
			}
			informCenter();
			try {
				JSONObject appObject = (JSONObject) jsonObject.get("ap");
				String appFileName = (String) appObject.get("listenFrom");
				this.agent.setApLogsDest(appFileName);
				System.out.println("appdilename" + appFileName);
				// String appSendTo = (String) appObject.get("sendTo");
				String appSendTo = sendTo + "/applicationLogs";
				System.out.println(appSendTo);
				if (appObject != null) {
					//Thread appThread = 
							new Thread(new Runnable(){
						private Agent agent;
						public Runnable init(Agent agent){
							this.agent=agent;
							return this;
						}
					
						@Override
						public void run() {
							ApplicationLogController alc = new ApplicationLogController(this.agent);
							try {
								System.out.println("usao u appThread");
								alc.loadApplicationLogs(appFileName, confFile,
										appSendTo);
							} catch (IOException | ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}.init(this.agent)).start();
					//appThread.start();
				}
			} catch (Exception e) {
				System.out.println("NO APP LOGS!");
			}
			try {
				JSONObject fwObject = (JSONObject) jsonObject.get("fw");
				String fwFileName = (String) fwObject.get("listenFrom");
				this.agent.setFwLogsDest(fwFileName);
				// String fwSendTo = (String) fwObject.get("sendTo");
				String fwSendTo = sendTo + "/logfirewall";
				//Thread fwThread = 
						new Thread(new Runnable(){
							private Agent agent;
							public Runnable init(Agent agent){
								this.agent=agent;
								return this;
							}
					@Override
					public void run() {
						System.out.println("usao u fwThread");
						LogFirewallController lfc = new LogFirewallController(this.agent);
						lfc.parse(fwFileName, confFile, fwSendTo);
					}
				}.init(this.agent)).start();
				//fwThread.start();
			} catch (Exception e) {
				System.out.println("NO FW LOGS!");
			}

			try {
				JSONObject lsObject = (JSONObject) jsonObject.get("ls");
				String lsFileName = (String) lsObject.get("listenFrom");
				this.agent.setServerLogsDest(lsFileName);
				// String lsSendTo = (String) lsObject.get("sendTo");
				String lsSendTo = sendTo + "/logserver";
				//Thread lsThread = 
						new Thread(new Runnable(){
							private Agent agent;
							public Runnable init(Agent agent){
								this.agent=agent;
								return this;
							} 
					@Override
					public void run() {
						System.out.println("usao u lsThread");
						LogServerController lsc = new LogServerController(this.agent);
						lsc.readLogs(lsFileName, confFile, lsSendTo);
					}
				}.init(this.agent)).start();
			
			} catch (Exception e) {
				System.out.println("NO LS LOGS!");
			}

			try {
				JSONObject osObject = (JSONObject) jsonObject.get("os");
				// String osSendTo = (String) osObject.get("sendTo");
				String osSendTo = sendTo + "/OSlogs";
				//Thread osThread = 
						new Thread(new Runnable(){
							private Agent agent;
							public Runnable init(Agent agent){
								this.agent=agent;
								return this;
							} 
					@Override
					public void run() {
						OperatingSystemLogController oslc = new OperatingSystemLogController(this.agent);
						try {
							System.out.println("uao u osThread");
							oslc.getOSlogs(confFile, osSendTo);
						} catch (ParseException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.init(this.agent)).start();
				//osThread.start();

			} catch (Exception e) {
				System.out.println("NO OS LOGS!");
			}

		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/applicationLogs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> createApplicationLog(
			@RequestBody ApplicationLog applicationlog) throws Exception {
		System.out.println("USLO DA SALJE APP LOGOVE");
		String sendTo = "https://" + this.agent.getParentIp() + ":"
				+ this.agent.getParentPort() + "/applicationLogs";
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		applicationLogController.sendToCenter(applicationlog, sendTo);
		return new ResponseEntity<ApplicationLog>(HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/logfirewall/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogFirewall> createFirewallLogs(
			@RequestBody LogFirewall logFirewall) throws Exception {
		System.out.println("USLO DA SALJE FW LOGOVE");
		String sendTo = "https://" + this.agent.getParentIp() + ":"
				+ this.agent.getParentPort() + "/logfirewall";
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		logFirewallController.sendToCenter(logFirewall, sendTo);
		return new ResponseEntity<LogFirewall>(HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/logserver/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LogServer> createServerLogs(
			@RequestBody LogServer logServer) throws Exception {
		System.out.println("USLO DA SALJE LS LOGOVE");
		String sendTo = "https://" + this.agent.getParentIp() + ":"
				+ this.agent.getParentPort() + "/logserver";
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		logServerController.sendToCenter(logServer, sendTo);
		return new ResponseEntity<LogServer>(HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(value = "/OSlogs/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> createOsLogs(
			@RequestBody OperatingSystemLog osLog) throws Exception {
		System.out.println("USLO DA SALJE OS LOGOVE");
		String sendTo = "https://" + this.agent.getParentIp() + ":"
				+ this.agent.getParentPort() + "/OSlogs";
		System.out.println("\n\n\nPARENT SALJE NA " + sendTo + " \n\n\n");
		osLogController.sendToCenter(osLog, sendTo);
		return new ResponseEntity<OperatingSystemLog>(HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/obicniget", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApplicationLog> getApplicationLog() {
		return new ResponseEntity<ApplicationLog>(HttpStatus.OK);
	}
	
	public void informCenter(){

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AgentDTO> entity = new HttpEntity<>(new AgentDTO(agent) ,headers);

        String response = "";

        while(!response.equals("Agent accepted by center.")){
            try {
                response = rest.postForObject("https://localhost:8443/api" +
                        "/agent/save", entity, String.class);
                System.out.println(response);
                if (response.equals("Agent accepted by center."))
                    break;
            } catch(Exception e){
                e.printStackTrace();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                continue;
            }
        }

    }
	
	@RequestMapping(value = "/change-parent/{newIp}/{newPort}/{newName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> changeParent(
			@PathVariable String newIp, @PathVariable String newPort,
			@PathVariable String newName) throws Exception {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ CONF_FILE_NAME;
		JSONObject jsonObject;
		jsonObject = (JSONObject) parser.parse(new FileReader(path));
		JSONObject parentObject = (JSONObject) jsonObject.get("parent");
		parentObject.put("name", newName);
		parentObject.put("ip", newIp);
		parentObject.put("port", newPort);
		jsonObject.put("parent", parentObject);
		ObjectMapper mapper = new ObjectMapper();
		String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(pretty);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
		return new ResponseEntity<OperatingSystemLog>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/change-listen-from/{logType}/{newVal}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OperatingSystemLog> changeListenFrom(
			@PathVariable String logType, @PathVariable String newVal) throws Exception {
		JSONParser parser = new JSONParser();
		String path = ".." + File.separator + "scripts" + File.separator
				+ CONF_FILE_NAME;
		JSONObject jsonObject;
		jsonObject = (JSONObject) parser.parse(new FileReader(path));
		JSONObject logDataObject = (JSONObject) jsonObject.get(logType);
		logDataObject.put("listenFrom", newVal);
		jsonObject.put(logType, logDataObject);
		ObjectMapper mapper = new ObjectMapper();
		String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(pretty);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
		return new ResponseEntity<OperatingSystemLog>(HttpStatus.CREATED);
	}
	
}

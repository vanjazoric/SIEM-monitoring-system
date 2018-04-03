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

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogServer;
import com.agent.service.LogFirewallService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/firewallLog")
public class LogFirewallController {

	public void parse() {

		ArrayList<LogFirewall> logs = new ArrayList<LogFirewall>();

		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + "firewallLogs.txt");
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int counter = 0;
			while ((line = in.readLine()) != null) {
				if (counter >= 15) {
					break;
				}
				String tokens[] = line.trim().split(" ");
				Long id = null;
				Agent agent = new Agent();
				Date timeStamp = sdf.parse(tokens[0] + " " + tokens[1]);
				String action = tokens[2];
				String protocol = tokens[3];
				String srcIp = tokens[4];
				String dstIp = tokens[5];
				String srcPort = tokens[6];
				String dstPort = tokens[7];
				int size = Integer.parseInt(tokens[8]);
				String tcpflags = tokens[9];
				String tcpsync = tokens[10];
				LogFirewall lf = new LogFirewall(id, timeStamp, agent, action,
						protocol, srcIp, dstIp, srcPort, dstPort, size,
						tcpflags, tcpsync);
				logs.add(lf);
				counter++;
			}
			for (LogFirewall lf : logs) {
				System.out.println(lf);
			}
			sendToCenter(logs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sendToCenter(ArrayList<LogFirewall> logs)
			throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost("http://localhost:8888/logfirewall/createall");
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			StringEntity postingString = new StringEntity(gson.toJson(logs));
			request.setEntity(postingString);
			request.setHeader("Content-type", "application/json");
			response = (CloseableHttpResponse) httpClient.execute(request);
		//	String json = EntityUtils.toString(response.getEntity());
		//	System.out.println(json);
		} catch (Exception ex) {
		} finally {
			response.close();
		}
	}
}

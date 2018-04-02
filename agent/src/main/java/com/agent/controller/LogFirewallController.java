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

import com.agent.domain.Agent;
import com.agent.domain.LogFirewall;
import com.agent.domain.LogServer;
import com.agent.service.LogFirewallService;

@RestController
@RequestMapping(value = "/firewallLog")
public class LogFirewallController {

	public void parse() {

		List<LogFirewall> logs = new ArrayList<LogFirewall>();

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
				Agent agent = null;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

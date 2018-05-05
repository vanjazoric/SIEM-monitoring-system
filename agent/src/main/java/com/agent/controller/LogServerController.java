package com.agent.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agent.domain.Agent;
import com.agent.domain.LogServer;
import com.agent.service.LogServerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping(value = "/logService")
public class LogServerController {

	@Autowired
	LogServerService logServerService;

	public void readLogs() {
		ArrayList<LogServer> logs = new ArrayList<>();
		File relativeFile = new File(".." + File.separator + "scripts"
				+ File.separator + "logserver.txt");
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					relativeFile.getCanonicalPath()));
			String line;
			while ((line = in.readLine()) != null) {
				String[] tokens = line.split(" ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date timeStamp = new Date();
				try {
					timeStamp = sdf.parse(tokens[0] + " " + tokens[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				LogServer l = new LogServer(null, timeStamp, tokens[2],
						tokens[3], tokens[5], tokens[4], tokens[6],
						Integer.valueOf(tokens[7]), Integer.valueOf(tokens[8]),
						new Agent());
				logs.add(l);
			}
			in.close();
			sendToCenter(logs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendToCenter(ArrayList<LogServer> logs) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpPost request = new HttpPost("http://localhost:8888/logserver");
			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
			StringEntity postingString = new StringEntity(gson.toJson(logs));
			request.setEntity(postingString);
			request.setHeader("Content-type", "application/json");
			response = (CloseableHttpResponse) httpClient.execute(request);
			// String json = EntityUtils.toString(response.getEntity());
			// System.out.println(json);
		} catch (Exception ex) {
		} finally {
			response.close();
		}
	}

}

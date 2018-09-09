package com.agent.domain;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class Agent {

	private String id;
	private String name;

	private String ip;
	private String port;
	private String parentName;
	private String parentIp;
	private String parentPort;
	private String centerIp;
	private ArrayList<String> childrenAgents;

	private Set<Log> logs;

	public Agent() {
		super();
	}

	public Agent(String id, String name, Set<Log> logs) {
		super();
		this.id = id;
		this.name = name;
		this.logs = logs;
	}

	public Agent(String id, String name, String ip, String port,
			String parentName, String parentIp, String parentPort,
			String centerIp, ArrayList<String> childrenAgents, Set<Log> logs) {
		super();
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.parentName = parentName;
		this.parentIp = parentIp;
		this.parentPort = parentPort;
		this.centerIp = centerIp;
		this.childrenAgents = childrenAgents;
		this.logs = logs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentIp() {
		return parentIp;
	}

	public void setParentIp(String parentIp) {
		this.parentIp = parentIp;
	}

	public String getParentPort() {
		return parentPort;
	}

	public void setParentPort(String parentPort) {
		this.parentPort = parentPort;
	}

	public String getCenterIp() {
		return centerIp;
	}

	public void setCenterIp(String centerIp) {
		this.centerIp = centerIp;
	}

	public ArrayList<String> getChildrenAgents() {
		return childrenAgents;
	}

	public void setChildrenAgents(ArrayList<String> childrenAgents) {
		this.childrenAgents = childrenAgents;
	}
	
	

}

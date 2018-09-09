package com.agent.DTO;

import java.util.ArrayList;
import java.util.Set;

import com.agent.domain.Agent;
import com.agent.domain.Log;


public class AgentDTO {

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
	
	public AgentDTO(){}
	
	public AgentDTO(Agent agent){
		this.id = agent.getId();
		this.name = agent.getName();
		this.ip = agent.getIp();
		this.port = agent.getPort();
		this.parentName = agent.getParentName();
		this.parentIp = agent.getParentIp();
		this.parentPort = agent.getParentPort();
		this.centerIp = agent.getCenterIp();
		this.childrenAgents = agent.getChildrenAgents();
		this.logs = agent.getLogs();	
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

	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}
	
	
	
}

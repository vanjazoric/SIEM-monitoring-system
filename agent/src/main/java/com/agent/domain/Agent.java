package com.agent.domain;


public class Agent {
	
	private String id;
	private String source;
	private String name;
	private String sendTo;
	private String confFile;
	public Agent() {
		super();
	}
	public Agent(String id, String source, String name, String sendTo,
			String confFile) {
		super();
		this.id = id;
		this.source = source;
		this.name = name;
		this.sendTo = sendTo;
		this.confFile = confFile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	public String getConfFile() {
		return confFile;
	}
	public void setConfFile(String confFile) {
		this.confFile = confFile;
	}

	
	
}

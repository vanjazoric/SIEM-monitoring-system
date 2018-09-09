package com.agent.domain;

import java.util.Date;

public class LogFirewall extends Log {
	private String action;
	private String protocol;
	private String srcIp;
	private String dstIp;
	private String srcPort;
	private String dstPort;
	private int size;
	private String tcpflags;
	private String tcpsync;

	public LogFirewall() {
		super();
	}

	public LogFirewall(String action, String protocol, String srcIp,
			String dstIp, String srcPort, String dstPort, int size,
			String tcpflags, String tcpsync) {
		super();
		this.action = action;
		this.protocol = protocol;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
		this.size = size;
		this.tcpflags = tcpflags;
		this.tcpsync = tcpsync;
	}

	public LogFirewall(String id, Date timeStamp, String agentName, String action,
			String protocol, String srcIp, String dstIp, String srcPort,
			String dstPort, int size, String tcpflags, String tcpsync) {
		super(id, timeStamp, agentName);
		this.action = action;
		this.protocol = protocol;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
		this.size = size;
		this.tcpflags = tcpflags;
		this.tcpsync = tcpsync;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getDstPort() {
		return dstPort;
	}

	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getTcpflags() {
		return tcpflags;
	}

	public void setTcpflags(String tcpflags) {
		this.tcpflags = tcpflags;
	}

	public String getTcpsync() {
		return tcpsync;
	}

	public void setTcpsync(String tcpsync) {
		this.tcpsync = tcpsync;
	}

	@Override
	public String toString() {
		return "LogFirewall [action=" + action + ", protocol=" + protocol
				+ ", srcIp=" + srcIp + ", dstIp=" + dstIp + ", srcPort="
				+ srcPort + ", dstPort=" + dstPort + ", size=" + size
				+ ", tcpflags=" + tcpflags + ", tcpsync=" + tcpsync + "]";
	}
	
	

}

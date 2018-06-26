package com.agent.domain;

import java.util.ArrayList;

public class LogPackage {
	private ArrayList<LogFirewall> logsFirewall;
	private ArrayList<ApplicationLog> applicationLogs;
	private ArrayList<LogServer> logsServer;
	private ArrayList<OperatingSystemLog> osLogs;

	public LogPackage() {
		super();
		this.logsFirewall = new ArrayList<LogFirewall>();
		this.applicationLogs = new ArrayList<ApplicationLog>();
		this.logsServer = new ArrayList<LogServer>();
		this.osLogs = new ArrayList<OperatingSystemLog>();
	}

	public LogPackage(ArrayList<LogFirewall> logsFirewall,
			ArrayList<ApplicationLog> applicationLogs,
			ArrayList<LogServer> logsServer,
			ArrayList<OperatingSystemLog> osLogs) {
		super();
		this.logsFirewall = logsFirewall;
		this.applicationLogs = applicationLogs;
		this.logsServer = logsServer;
		this.osLogs = osLogs;
	}

	public ArrayList<LogFirewall> getLogsFirewall() {
		return logsFirewall;
	}

	public void setLogsFirewall(ArrayList<LogFirewall> logsFirewall) {
		this.logsFirewall = logsFirewall;
	}

	public ArrayList<ApplicationLog> getApplicationLogs() {
		return applicationLogs;
	}

	public void setApplicationLogs(ArrayList<ApplicationLog> applicationLogs) {
		this.applicationLogs = applicationLogs;
	}

	public ArrayList<LogServer> getLogsServer() {
		return logsServer;
	}

	public void setLogsServer(ArrayList<LogServer> logsServer) {
		this.logsServer = logsServer;
	}

	public ArrayList<OperatingSystemLog> getOsLogs() {
		return osLogs;
	}

	public void setOsLogs(ArrayList<OperatingSystemLog> osLogs) {
		this.osLogs = osLogs;
	}

}

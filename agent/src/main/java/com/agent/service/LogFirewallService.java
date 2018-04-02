package com.agent.service;

import java.util.List;

import com.agent.domain.LogFirewall;

public interface LogFirewallService {
	List<LogFirewall> loadFirewallLogs(String path);
}

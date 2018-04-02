package com.agent.service;

import java.util.List;

import com.agent.domain.LogServer;

public interface LogServerService {

	public List<LogServer> loadServerLogs();
	
}

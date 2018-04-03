package com.center.service;

import java.util.Collection;

import com.center.domain.LogServer;

public interface LogServerService {

	Collection<LogServer> findAll();

	LogServer findOne(Long id);

	LogServer create(LogServer logserver) throws Exception;

	LogServer update(LogServer logserver) throws Exception;

	void delete(LogServer logserver) throws Exception;

}
package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.LogServer;
import com.center.repository.LogServerRepository;

@Service
public class LogServerServiceImpl implements LogServerService{
	
	@Autowired
	private LogServerRepository logserverRepository;

	@Override
	public Collection<LogServer> findAll() {
		// TODO Auto-generated method stub
		return logserverRepository.findAll();
	}

	@Override
	public LogServer findOne(Long id) {
		// TODO Auto-generated method stub
		return logserverRepository.findOne(id);
	}

	@Override
	public LogServer create(LogServer logserver) throws Exception {
		// TODO Auto-generated method stub
		return logserverRepository.save(logserver);
		
	}

	@Override
	public LogServer update(LogServer logserver) throws Exception {
		// TODO Auto-generated method stub
		return logserverRepository.save(logserver);
	}

	@Override
	public void delete(LogServer logserver) throws Exception {
		// TODO Auto-generated method stub
		logserverRepository.delete(logserver);
		
	}

	
}

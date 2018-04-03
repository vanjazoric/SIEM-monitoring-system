package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.Log;
import com.center.repository.LogRepository;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	private LogRepository logRepository;

	@Override
	public Collection<Log> findAll() {
		// TODO Auto-generated method stub
		return logRepository.findAll();
	}

	@Override
	public Log findOne(Long id) {
		// TODO Auto-generated method stub
		return logRepository.findOne(id);
	}

	@Override
	public Log create(Log log) throws Exception {
		// TODO Auto-generated method stub
		return logRepository.save(log);
		
	}

	@Override
	public Log update(Log log) throws Exception {
		// TODO Auto-generated method stub
		return logRepository.save(log);
	}

	@Override
	public void delete(Log log) throws Exception {
		// TODO Auto-generated method stub
		logRepository.delete(log);
		
	}

	
}

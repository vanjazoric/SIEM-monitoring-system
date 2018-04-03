package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.ApplicationLog;
import com.center.repository.ApplicationLogRepository;

@Service
public class ApplicationLogServiceImpl implements ApplicationLogService{
	
	@Autowired
	private ApplicationLogRepository applicationlogRepository;

	@Override
	public Collection<ApplicationLog> findAll() {
		// TODO Auto-generated method stub
		return applicationlogRepository.findAll();
	}

	@Override
	public ApplicationLog findOne(Long id) {
		// TODO Auto-generated method stub
		return applicationlogRepository.findOne(id);
	}

	@Override
	public ApplicationLog create(ApplicationLog applicationlog) throws Exception {
		// TODO Auto-generated method stub
		return applicationlogRepository.save(applicationlog);
		
	}

	@Override
	public ApplicationLog update(ApplicationLog applicationlog) throws Exception {
		// TODO Auto-generated method stub
		return applicationlogRepository.save(applicationlog);
	}

	@Override
	public void delete(ApplicationLog applicationlog) throws Exception {
		// TODO Auto-generated method stub
		applicationlogRepository.delete(applicationlog);
		
	}

	
}

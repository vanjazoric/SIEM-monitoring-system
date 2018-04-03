package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.OperatingSystemLog;
import com.center.repository.OperatingSystemLogRepository;

@Service
public class OperatingSystemLogServiceImpl implements OperatingSystemLogService{
	
	@Autowired
	private OperatingSystemLogRepository operatingsystemlogRepository;

	@Override
	public Collection<OperatingSystemLog> findAll() {
		// TODO Auto-generated method stub
		return operatingsystemlogRepository.findAll();
	}

	@Override
	public OperatingSystemLog findOne(Long id) {
		// TODO Auto-generated method stub
		return operatingsystemlogRepository.findOne(id);
	}

	@Override
	public OperatingSystemLog create(OperatingSystemLog operatingsystemlog) throws Exception {
		// TODO Auto-generated method stub
		return operatingsystemlogRepository.save(operatingsystemlog);
		
	}

	@Override
	public OperatingSystemLog update(OperatingSystemLog operatingsystemlog) throws Exception {
		// TODO Auto-generated method stub
		return operatingsystemlogRepository.save(operatingsystemlog);
	}

	@Override
	public void delete(OperatingSystemLog operatingsystemlog) throws Exception {
		// TODO Auto-generated method stub
		operatingsystemlogRepository.delete(operatingsystemlog);
		
	}

	
}

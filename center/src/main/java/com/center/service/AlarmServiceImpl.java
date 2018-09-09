package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.Alarm;
import com.center.repository.AlarmRepository;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmRepository alarmRepository;
	
	@Override
	public Alarm save(Alarm alarm) {
		// TODO Auto-generated method stub
		return alarmRepository.save(alarm);
	}

	@Override
	public Alarm findOne(String id) {
		// TODO Auto-generated method stub
		return alarmRepository.findOne(id);
	}

	@Override
	public void delete(Alarm alarm) {
		// TODO Auto-generated method stub
		alarmRepository.delete(alarm);
	}

	@Override
	public Collection<Alarm> findAll() {
		// TODO Auto-generated method stub
		return alarmRepository.findAll();
	}
}

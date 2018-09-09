package com.center.service;

import java.util.Collection;

import com.center.domain.Alarm;

public interface AlarmService {
	Alarm save(Alarm alarm);
	Alarm findOne(String id);
	void delete(Alarm alarm);
	Collection<Alarm> findAll();
}

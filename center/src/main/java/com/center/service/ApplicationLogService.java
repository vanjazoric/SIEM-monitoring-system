package com.center.service;

import java.util.Collection;

import com.center.domain.ApplicationLog;

public interface ApplicationLogService {

	Collection<ApplicationLog> findAll();

	ApplicationLog findOne(Long id);

	ApplicationLog save(ApplicationLog applicationlog) throws Exception;

	void delete(Long id) throws Exception;

}
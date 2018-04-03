package com.center.service;

import java.util.Collection;

import com.center.domain.ApplicationLog;

public interface ApplicationLogService {

	Collection<ApplicationLog> findAll();

	ApplicationLog findOne(Long id);

	ApplicationLog create(ApplicationLog applicationlog) throws Exception;

	ApplicationLog update(ApplicationLog applicationlog) throws Exception;

	void delete(ApplicationLog applicationlog) throws Exception;

}
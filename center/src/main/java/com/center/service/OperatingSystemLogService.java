package com.center.service;

import java.util.Collection;

import com.center.domain.OperatingSystemLog;

public interface OperatingSystemLogService {

	Collection<OperatingSystemLog> findAll();

	OperatingSystemLog findOne(Long id);

	OperatingSystemLog create(OperatingSystemLog operatingsystemlog) throws Exception;

	OperatingSystemLog update(OperatingSystemLog operatingsystemlog) throws Exception;

	void delete(OperatingSystemLog operatingsystemlog) throws Exception;

}
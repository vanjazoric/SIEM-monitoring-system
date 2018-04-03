package com.center.service;

import java.util.Collection;

import com.center.domain.Log;

public interface LogService {

	Collection<Log> findAll();

	Log findOne(Long id);

	Log create(Log log) throws Exception;

	Log update(Log log) throws Exception;

	void delete(Log log) throws Exception;

}
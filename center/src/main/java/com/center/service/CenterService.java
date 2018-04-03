package com.center.service;

import java.util.Collection;

import com.center.domain.Center;

public interface CenterService {

	Collection<Center> findAll();

	Center findOne(Long id);

	Center create(Center center) throws Exception;

	Center update(Center center) throws Exception;

	void delete(Center center) throws Exception;

}
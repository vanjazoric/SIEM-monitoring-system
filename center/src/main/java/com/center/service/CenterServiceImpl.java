package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.Center;
import com.center.repository.CenterRepository;

@Service
public class CenterServiceImpl implements CenterService{
	
	@Autowired
	private CenterRepository centerRepository;

	@Override
	public Collection<Center> findAll() {
		// TODO Auto-generated method stub
		return centerRepository.findAll();
	}

	@Override
	public Center findOne(Long id) {
		// TODO Auto-generated method stub
		return centerRepository.findOne(id);
	}

	@Override
	public Center create(Center center) throws Exception {
		// TODO Auto-generated method stub
		return centerRepository.save(center);
		
	}

	@Override
	public Center update(Center center) throws Exception {
		// TODO Auto-generated method stub
		return centerRepository.save(center);
	}

	@Override
	public void delete(Center center) throws Exception {
		// TODO Auto-generated method stub
		centerRepository.delete(center);
		
	}

	
}

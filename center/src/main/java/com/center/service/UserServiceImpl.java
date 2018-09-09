package com.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.User;
import com.center.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User create(User user){
		return userRepository.save(user);
	}
	
	@Override
	public void deleteAll(){
		userRepository.deleteAll();
	}
	
	@Override
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
}

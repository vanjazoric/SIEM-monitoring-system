package com.center.service;

import com.center.domain.User;

public interface UserService {
	
	User create(User user);
	void deleteAll();
	User findByUsername(String username);
}

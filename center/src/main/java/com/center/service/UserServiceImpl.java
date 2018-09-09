package com.center.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	@Override
	public boolean validatePassword(String password) {
        if (password.length() < 16) return false;

        Pattern ucasePattern = Pattern.compile("[A-Z]+");
        Matcher ucaseMatcher = ucasePattern.matcher(password);
        if (!ucaseMatcher.find()) return false;

        Pattern lcasePattern = Pattern.compile("[a-z]+");
        Matcher lcaseMatcher = lcasePattern.matcher(password);
        if (!lcaseMatcher.find()) return false;

        Pattern specCharPattern = Pattern.compile("[^A-Za-z0-9]+");
        Matcher specCharMatcher = specCharPattern.matcher(password);
        if (!specCharMatcher.find()) return false;

        Pattern numPattern = Pattern.compile("[0-9]+");
        Matcher numMatcher = numPattern.matcher(password);
        if (!numMatcher.find()) return false;

        return true;
    }
	
}

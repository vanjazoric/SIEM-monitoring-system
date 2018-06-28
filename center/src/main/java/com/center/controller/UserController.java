package com.center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.center.DTO.LoginDTO;
import com.center.domain.User;
import com.center.repository.UserRepository;
import com.center.security.TokenUtils;


@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;

	
	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginDTO user){
		System.out.println("--"+user.getUsername()+"  "+user.getPassword());
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails details=userDetailsService.loadUserByUsername(user.getUsername());
			
			
			return new ResponseEntity<String>(tokenUtils.generateToken(details), HttpStatus.OK);
		}catch (Exception ex) {
            return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
        }
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "/users", 
	method = RequestMethod.PUT,
	consumes = MediaType.APPLICATION_JSON_VALUE,
	produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user,@RequestHeader HttpHeaders headers){
		String activUserName=tokenUtils.getUsernameFromToken(headers.get("X-Auth-Token").get(0).toString());
		User activeUser=userRepository.findOne(activUserName);
		User exists = userRepository.findOne(user.getId());
		
		if(exists== null){
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		User savedUser=null;
		if(!activeUser.getUsername().equals(exists.getUsername())){
			boolean isAdmin=false;
			if(activeUser.getRole().equals("ADMIN")){
				isAdmin=true;
			}
			if(!isAdmin){
				return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
			}
		}
		try{
			if(user.getPassword()==null){
				user.setPassword(exists.getPassword());
			}else{
				PasswordEncoder encoder=new BCryptPasswordEncoder();
				String encodedPassword=encoder.encode(user.getPassword());
				user.setPassword(encodedPassword);
			}
			savedUser=userRepository.save(user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);		
	}
	
}

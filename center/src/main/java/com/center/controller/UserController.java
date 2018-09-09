package com.center.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.center.DTO.CurrentUserDTO;
import com.center.DTO.LoginDTO;
import com.center.domain.PasswordChange;
import com.center.domain.User;
import com.center.security.TokenUtils;
import com.center.service.UserService;

@RestController
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	
    @Autowired
    HttpServletRequest request;
    
    @Autowired
    PasswordEncoder passwordEncoder;

	
    @PostMapping(value="/login", consumes="application/json")
    public ResponseEntity<CurrentUserDTO> signIn(@RequestBody LoginDTO credentials) {
        logger.debug("Accessing POST /login");
        System.out.println("usaooooooooooo");
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword());
            System.out.println("usaooooooooooo"+credentials.getUsername());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails details = userDetailsService.loadUserByUsername(credentials.getUsername());

            String jwtToken = tokenUtils.generateToken(details);
            String username = tokenUtils.getUsernameFromToken(jwtToken);
            User user = userService.findByUsername(username);
            String role = user.getRole();
            CurrentUserDTO currentUser = new CurrentUserDTO(username, role, jwtToken);
            return new ResponseEntity<>(currentUser, HttpStatus.OK);

        } catch (Exception ex) {
        	ex.printStackTrace();
            if (request.getAttribute("blocked") != null)
                return new ResponseEntity<>(HttpStatus.LOCKED);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping(value="/change-password", produces="application/json")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChange passwordChange) {
        logger.debug("Accessing POST /change-password");
        User user = userService.findByUsername(passwordChange.getUsername());
        if (!passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>("{ \"message\": \"Password change failed.\"}", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(passwordChange.getNewPassword()));
        userService.create(user);

        return new ResponseEntity<>("{ \"message\": \"Password change successful.\"}", HttpStatus.OK);
    }
    
	
}

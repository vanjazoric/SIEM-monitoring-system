package com.center.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.center.domain.Role;
import com.center.domain.User;
import com.center.repository.RoleRepository;
import com.center.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    private LoginAttemptService loginAttemptService;
	
    @Autowired
    private HttpServletRequest request;
	
    public UserDetails loadUserByUsername(String username) {
    	String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            request.setAttribute("blocked", true);
            throw new RuntimeException("blocked");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Role role = roleRepository.findRoleByName(user.getRole());
        List<String> permissions = role.getPermissions();
        for (String string : permissions) {
			System.out.println(string);
		}
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permissions.size(); i++) {
            stringBuilder.append(permissions.get(i));
            if (i != permissions.size() - 1) stringBuilder.append(",");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList(stringBuilder.toString()));
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}

package com.agent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agent.domain.Agent;

@Service
public class AgentDetailsService implements UserDetailsService {

    @Autowired
    Agent agent;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if (!agent.getChildrenAgents().contains(s))
            throw new UsernameNotFoundException(s);

        if (agent.getChildrenAgents().contains(s))
            return new User(s, "",
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

        return null;
    }
	
}

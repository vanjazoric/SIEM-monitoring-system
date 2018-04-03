package com.center.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.LogFirewall;
import com.center.repository.LogFirewallRepository;

@Service
public class LogFirewallServiceImpl implements LogFirewallService{
	
	@Autowired
	private LogFirewallRepository logfirewallRepository;

	@Override
	public Collection<LogFirewall> findAll() {
		// TODO Auto-generated method stub
		return logfirewallRepository.findAll();
	}

	@Override
	public LogFirewall findOne(Long id) {
		// TODO Auto-generated method stub
		return logfirewallRepository.findOne(id);
	}

	@Override
	public LogFirewall create(LogFirewall logfirewall) throws Exception {
		// TODO Auto-generated method stub
		return logfirewallRepository.save(logfirewall);
		
	}

	@Override
	public LogFirewall update(LogFirewall logfirewall) throws Exception {
		// TODO Auto-generated method stub
		return logfirewallRepository.save(logfirewall);
	}

	@Override
	public void delete(LogFirewall logfirewall) throws Exception {
		// TODO Auto-generated method stub
		logfirewallRepository.delete(logfirewall);
		
	}

	
}

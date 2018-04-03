package com.center.service;

import java.util.Collection;

import com.center.domain.LogFirewall;

public interface LogFirewallService {

	Collection<LogFirewall> findAll();

	LogFirewall findOne(Long id);

	LogFirewall create(LogFirewall logfirewall) throws Exception;

	LogFirewall update(LogFirewall logfirewall) throws Exception;

	void delete(LogFirewall logfirewall) throws Exception;

}
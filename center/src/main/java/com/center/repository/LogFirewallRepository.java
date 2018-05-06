package com.center.repository;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.center.domain.LogFirewall;

public interface LogFirewallRepository extends MongoRepository<LogFirewall, String> {
	public ArrayList<LogFirewall> findLogFirewallByTimeStamp(Date timeStamp);
	public ArrayList<LogFirewall> findLogFirewallByProtocol(String protocol);
	public ArrayList<LogFirewall> findLogFirewallByAction(String action);

}
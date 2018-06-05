package com.center.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.center.domain.LogFirewall;

public interface LogFirewallRepository extends
		MongoRepository<LogFirewall, String> {

	@Query(value = "{'$or':[ {'protocol':?0}, {'protocol':?1}, {'protocol':?2} ]}")
	public ArrayList<LogFirewall> findLogFirewallByProtocol(String p1,
			String p2, String p3);

	public ArrayList<LogFirewall> findLogFirewallBySrcIp(String srcIp);

	public ArrayList<LogFirewall> findLogFirewallByDstIp(String dstIp);

	@Query(value = "{'$or':[ {'action':?0}, {'action':?1}, {'action':?2} ]}")
	public ArrayList<LogFirewall> findFirewallLogsByAction(String p1,
			String p2, String p3);

	@Query(value = "{ '_class' : ?0 }")
	public ArrayList<LogFirewall> findAllByClass(String className);

}
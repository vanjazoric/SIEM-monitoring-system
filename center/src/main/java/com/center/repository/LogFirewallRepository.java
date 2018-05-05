package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.center.domain.LogFirewall;

public interface LogFirewallRepository extends MongoRepository<LogFirewall, String> {

}
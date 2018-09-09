package com.center;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.center.domain.Role;
import com.center.domain.User;
import com.center.service.AgentService;
import com.center.service.RoleService;
import com.center.service.UserService;

@Component
public class InitApplication implements ApplicationRunner {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AgentService agentService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		userService.deleteAll();
		User u = new User("admin", "$2a$04$LJ9pxnsn//b4O5sZm8uEm.ldUIH8RIq8OTcpj3mpGs1XmV8MJq57W", "admin@gmail.com",
				"ADMIN");
		userService.create(u);

		roleService.deleteAll();
		Role adminRole = new Role();
		adminRole.setName("ADMIN");

		List<String> adminPermissions = new ArrayList<>();
		adminPermissions.add("READ_LOGS");
		adminPermissions.add("READ_FIRED_ALARMS");
		adminPermissions.add("WRITE_ALARMS");
		adminPermissions.add("READ_ALARMS");
		adminPermissions.add("UPDATE_ALARMS");
		adminPermissions.add("DELETE_ALARMS");
		adminPermissions.add("WRITE_AGENTS");
		adminPermissions.add("READ_AGENTS");
		adminRole.setPermissions(adminPermissions);
		Role operatorRole = new Role();
		operatorRole.setName("OPERATOR");
		List<String> operatorPermissions = new ArrayList<>();
		operatorPermissions.add("READ_LOGS");
		operatorPermissions.add("READ_ALARMS");
		operatorRole.setPermissions(operatorPermissions);
		Role agentRole = new Role();
		agentRole.setName("AGENT");
		List<String> agentPermissions = new ArrayList<>();
		agentPermissions.add("WRITE_LOGS");
		agentPermissions.add("WRITE_AGENTS");
		agentRole.setPermissions(agentPermissions);
		roleService.save(adminRole);
		roleService.save(operatorRole);
		roleService.save(agentRole);

		userService.deleteAll();
		User admin1 = new User("admin", passwordEncoder.encode("admin1"), "ADMIN");
		userService.create(admin1);
		User operator1 = new User("vanja", passwordEncoder.encode("vanja"), "OPERATOR");
		User operator2 = new User("ana", passwordEncoder.encode("ana"), "OPERATOR");
		User operator3 = new User("dule", passwordEncoder.encode("dule"), "OPERATOR");
		User operator4 = new User("novak", passwordEncoder.encode("novak"), "OPERATOR");
		userService.create(operator1);
		userService.create(operator2);
		userService.create(operator3);
		userService.create(operator4);
		User agent1 = new User("agent1", "", "AGENT");
		User agent2 = new User("agent2", "", "AGENT");
		User agent3 = new User("agent3", "", "AGENT");
		User agent4 = new User("agent4", "", "AGENT");
		User siemCenter = new User("siem-center", "", "AGENT");
		userService.create(agent1);
		userService.create(agent2);
		userService.create(agent3);
		userService.create(agent4);
		userService.create(siemCenter);
	}
}

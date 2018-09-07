package com.center;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.center.domain.Role;
import com.center.domain.User;
import com.center.service.RoleService;
import com.center.service.UserService;

@Component
public class InitApplication implements ApplicationRunner{

	@Autowired
	UserService userService;
	
	@Autowired
    RoleService roleService;
	
	@Override
    public void run(ApplicationArguments args) throws Exception {
		
		userService.deleteAll();
		User u=new User("admin","$2a$04$LJ9pxnsn//b4O5sZm8uEm.ldUIH8RIq8OTcpj3mpGs1XmV8MJq57W","admin@gmail.com","ADMIN");
		userService.create(u);
		
	    roleService.deleteAll();
	    Role adminRole = new Role();
	    adminRole.setName("ADMIN");
	    List<String> adminPermissions = new ArrayList<>();
        adminPermissions.add("CHANGE_PASSWORD");
        adminRole.setPermissions(adminPermissions);
        roleService.save(adminRole);
	}
}

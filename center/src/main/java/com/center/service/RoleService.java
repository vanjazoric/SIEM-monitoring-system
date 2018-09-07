package com.center.service;

import com.center.domain.Role;

public interface RoleService {

	public Role save(Role role);
	public Role findByName(String name);
	public void deleteAll();
	
}

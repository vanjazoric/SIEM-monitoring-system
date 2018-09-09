package com.center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.center.domain.Role;
import com.center.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
    RoleRepository roleRepository;
	
	@Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    public void deleteAll() {
        roleRepository.deleteAll();
    }
	
}

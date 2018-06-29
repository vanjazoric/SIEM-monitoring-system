/**
 * 
 */
package com.center.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Vanja
 *
 */
@Document(collection="roles")
public class Role {

	@Id
	private String id;
	private String role;
	
	public Role() {
		super();
	}
	public Role(String id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	
}

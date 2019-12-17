package com.teacheron.sales.entities;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
public class UserEntry {
	
	@QuerySqlField(index = true)
	private Integer id;

	@QuerySqlField(index = true)
	private String firstName;
	
	@QuerySqlField(index = true)
	private String lastName;
	
	@QuerySqlField(index = true)
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}

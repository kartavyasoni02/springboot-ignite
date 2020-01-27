package com.teacheron.sales.entities;

import org.apache.ignite.cache.query.annotations.QuerySqlField;
/**
 * @author kartavya
 *
 */
public class UserEntry {
	
	@QuerySqlField(index = true)
	private Integer id;

	@QuerySqlField(index = true)
	private String firstName;
	
	@QuerySqlField(index = true)
	private String lastName;
	
	@QuerySqlField(index = true)
	private String address;
	
	private String emailId;
	
	public UserEntry() {
		super();
	}

	public UserEntry(Integer id, String firstName, String lastName, String address, String emailId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emailId = emailId;
	}

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}

package com.teacheron.sales.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class UserDto {

	private Integer id;

	@NotNull(message = "{error.user.firstName.null}")
	@NotEmpty(message = "{error.user.firstName.empty}")
	@Size(max = 20, message = "{error.user.firstName.max}")
	private String firstName;

	@NotNull(message = "{error.user.lastName.null}")
	@NotEmpty(message = "{error.user.lastName.empty}")
	@Size(max = 20, message = "{error.user.lastName.max}")
	private String lastName;
	
	@NotNull(message = "{error.user.address.null}")
	@NotEmpty(message = "{error.user.address.empty}")
	@Size(max = 50, message = "{error.user.address.max}")
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

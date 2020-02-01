package com.teacheron.sales.service;

import java.util.List;

import javax.validation.Valid;

import org.apache.ignite.client.ClientException;

import com.teacheron.sales.domain.UserDomain;
import com.teacheron.sales.dto.UserDto;

public interface UserService extends GenericService<UserDomain>{

    public List<UserDto> getUsers() throws ClientException, Exception;

	public Integer saveUser(@Valid UserDto userDto) throws ClientException, Exception;

	String getUser(String emailId) throws ClientException, Exception;

}

package com.teacheron.sales.service;

import java.util.List;

import javax.validation.Valid;

import org.apache.ignite.client.ClientException;

import com.teacheron.sales.dto.UserDto;

public interface UserService {

	public UserDto getUser(Integer userId);

    public List<UserDto> getUsers() throws ClientException, Exception;

	public UserDto saveUser(@Valid UserDto userDto) throws ClientException, Exception;
	
	public UserDto updateUser(@Valid UserDto userDto);

	public void deleteUser(@Valid UserDto userDto);

}

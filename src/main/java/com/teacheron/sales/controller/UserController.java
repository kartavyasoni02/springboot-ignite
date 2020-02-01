package com.teacheron.sales.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teacheron.sales.dto.UserDto;
import com.teacheron.sales.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	/*
	 *  fetch data by Ignite cache based on email Id
	 */
	@GetMapping("/{emailId}")
	public String getUser(@PathVariable(name = "emailId") String emailId) throws Exception{
		return userService.getUser(emailId);
	}

	/*
	 *  Insert data into mysql db and update cache as email Id key
	 */
	@GetMapping()
	public List<UserDto> getUser() throws Exception{
		return userService.getUsers();
	}
	
	/*
	 *  get all users data from mysql db
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer saveUser(@Valid @RequestBody UserDto userDto) throws Exception{
		return userService.saveUser(userDto);
	}
}

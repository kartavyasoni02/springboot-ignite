package com.teacheron.sales.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.ignite.client.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/{userId}")
	public UserDto getUser(@PathVariable(name = "userId") Integer userId){
		return userService.getUser(userId);
	}

	@GetMapping()
	public List<UserDto> getUser() throws ClientException, Exception{
		return userService.getUsers();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto saveUser(@Valid @RequestBody UserDto userDto) throws ClientException, Exception{
		return userService.saveUser(userDto);
	}
	
	@PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto updateUser(@PathVariable(name = "userId") Integer userId, @Valid @RequestBody UserDto userDto){
		return userService.updateUser(userDto);
	}
	
	@DeleteMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteUser(@PathVariable(name = "userId") Integer userId, @Valid @RequestBody UserDto userDto){
		userService.deleteUser(userDto);
	}
}

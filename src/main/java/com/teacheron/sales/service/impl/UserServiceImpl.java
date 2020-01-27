package com.teacheron.sales.service.impl;

import java.util.List;

import javax.validation.Valid;

import org.apache.ignite.client.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teacheron.sales.dto.UserDto;
import com.teacheron.sales.literals.EmailLiterals;
import com.teacheron.sales.mapper.UserMapper;
import com.teacheron.sales.repositories.UserRepository;
import com.teacheron.sales.service.UserService;
import com.teacheron.sales.utility.EmailContents;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private MailClient mailClient;
	
	@Autowired
	private UserRepository userStore;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<UserDto> getUsers() throws ClientException, Exception {
		return userMapper.mapToDtos(userStore.getAllUsers());
	}
	
	@Override
	public String getUser(String emailId) throws ClientException, Exception {
		return userStore.getUserByEmailid(emailId);
	}

	@Override
	public UserDto saveUser(@Valid UserDto userDto) throws ClientException, Exception {
		return userMapper.mapToDto(userStore.createUserEntry(userMapper.mapToEntry(userDto)));
	}

	@Override
	public UserDto updateUser(@Valid UserDto userDto) {
		return null;
	}

	@Override
	public void deleteUser(@Valid UserDto userDto) {
	}

	private void sendThankYouMail() {
		EmailContents emailContents = new EmailContents("kartavya.soni02@gmail.com", EmailLiterals.THANK_YOU, EmailLiterals.SUCCESS);
		emailContents.addEmailContents(EmailLiterals.CUSTOMER_FIRST_NAME_PARAM, "Kartavya");
		mailClient.prepareAndSend(emailContents);
	}

}

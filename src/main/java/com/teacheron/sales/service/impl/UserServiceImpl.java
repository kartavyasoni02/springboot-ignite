package com.teacheron.sales.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.apache.ignite.client.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teacheron.sales.dao.GenericDAO;
import com.teacheron.sales.dao.UserDao;
import com.teacheron.sales.dto.UserDto;
import com.teacheron.sales.entities.UserEntry;
import com.teacheron.sales.literals.EmailLiterals;
import com.teacheron.sales.mapper.UserMapper;
import com.teacheron.sales.service.UserService;
import com.teacheron.sales.utility.EmailContents;

@Service
public class UserServiceImpl extends GenericServiceImpl<UserEntry> implements UserService {
	
	@Autowired
	private MailClient mailClient;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public GenericDAO<UserEntry, Integer> getBaseDAO() {
		return userDao;
	}
	
	@PostConstruct
	public void loadCache() throws ClientException, Exception {
		userDao.loadCache();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserDto> getUsers() throws Exception {
		return userMapper.mapToDtos(userDao.findAll());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public String getUser(String emailId) throws Exception {
		return userDao.getUserByEmailid(emailId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager", rollbackFor = Throwable.class)
	public Integer saveUser(@Valid UserDto userDto) throws Exception {
		return userDao.createUser(userMapper.mapToEntry(userDto));
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

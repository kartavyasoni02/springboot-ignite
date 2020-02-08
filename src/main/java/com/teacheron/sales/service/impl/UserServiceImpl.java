package com.teacheron.sales.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ignite.client.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teacheron.sales.dao.GenericDAO;
import com.teacheron.sales.dao.UserDao;
import com.teacheron.sales.domain.UserDomain;
import com.teacheron.sales.dto.UserDto;
import com.teacheron.sales.exceptions.UserAlreadyExistException;
import com.teacheron.sales.mapper.UserMapper;
import com.teacheron.sales.service.UserService;

@Service
public class UserServiceImpl extends GenericServiceImpl<UserDomain> implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public GenericDAO<UserDomain, Integer> getBaseDAO() {
		return userDao;
	}
	
	//@PostConstruct
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
	public Integer saveUser(UserDto userDto) throws Exception {
		UserDomain userDomain = userDao.findByEmailId(userDto.getEmailId());
		if(userDomain!=null) {
			throw new UserAlreadyExistException("User already exist");
		}
		return userDao.createUser(userMapper.mapToDomain(userDto));
	}
}

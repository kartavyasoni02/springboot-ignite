package com.teacheron.sales.dao;

import org.apache.ignite.client.ClientException;

import com.teacheron.sales.domain.UserDomain;

public interface UserDao extends GenericDAO<UserDomain, Integer>{

	void loadCache() throws ClientException, Exception;

	Integer createUser(UserDomain user) throws ClientException, Exception;

	UserDomain findByEmailId(String emailId);

	String getUserByEmailid(String emailId) throws ClientException, Exception;

}

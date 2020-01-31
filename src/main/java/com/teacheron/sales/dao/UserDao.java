package com.teacheron.sales.dao;

import org.apache.ignite.client.ClientException;

import com.teacheron.sales.entities.UserEntry;

public interface UserDao extends GenericDAO<UserEntry, Integer>{

	String getUserByEmailid(String emailId) throws ClientException, Exception;

	void loadCache() throws ClientException, Exception;

	void updateCache(UserEntry user) throws ClientException, Exception;

	Integer createUser(UserEntry user) throws ClientException, Exception;

}

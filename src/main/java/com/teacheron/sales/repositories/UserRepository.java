package com.teacheron.sales.repositories;

import java.util.List;

import org.apache.ignite.client.ClientException;

import com.teacheron.sales.entities.UserEntry;

public interface UserRepository {

	UserEntry createUserEntry(UserEntry userEntry) throws ClientException, Exception;

	List<UserEntry> getAllUsers() throws ClientException, Exception;

	void updateCache(UserEntry user) throws ClientException, Exception;

	String getUserByEmailid(String emailId) throws ClientException, Exception;

	void loadCache() throws ClientException, Exception;

}

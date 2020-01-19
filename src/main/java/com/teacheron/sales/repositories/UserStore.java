package com.teacheron.sales.repositories;

import java.util.List;

import org.apache.ignite.client.ClientException;

import com.teacheron.sales.entities.UserEntry;

public interface UserStore {

	UserEntry createUserEntry(UserEntry userEntry) throws ClientException, Exception;

	List<UserEntry> getAllUsers() throws ClientException, Exception;

}

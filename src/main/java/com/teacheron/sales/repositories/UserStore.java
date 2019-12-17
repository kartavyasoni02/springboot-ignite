package com.teacheron.sales.repositories;

import java.util.List;

import com.teacheron.sales.entities.UserEntry;

public interface UserStore {

	UserEntry createUserEntry(UserEntry userEntry);

	List<UserEntry> getAllUsers();

}

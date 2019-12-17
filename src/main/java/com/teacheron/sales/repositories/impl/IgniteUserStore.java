package com.teacheron.sales.repositories.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.cache.Cache;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.teacheron.sales.entities.CacheNames;
import com.teacheron.sales.entities.UserEntry;
import com.teacheron.sales.repositories.UserStore;

@Component
public class IgniteUserStore implements UserStore {
	
	@Autowired
    private Ignite ignite;
	
	@Override
    public UserEntry createUserEntry(UserEntry userEntry) {
		final IgniteCache<String, UserEntry> userCache = getUserCache();
        // insert into the key value store
        userCache.put(userEntry.getFirstName(), userEntry);
		return userEntry;
        
    }
	
	@Override
    public List<UserEntry> getAllUsers() {
        final String sql = "select * from UserEntry";
        SqlQuery<String, UserEntry> query = new SqlQuery<>(UserEntry.class, sql);
        return getUserCache().query(query).getAll()
                .stream()
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

    }
	
	public IgniteCache<String, UserEntry> getUserCache() {
        return ignite.getOrCreateCache(CacheNames.Users.name());
    }

}

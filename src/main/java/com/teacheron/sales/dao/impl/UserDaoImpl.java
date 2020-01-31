package com.teacheron.sales.dao.impl;

import java.util.List;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.teacheron.sales.dao.UserDao;
import com.teacheron.sales.entities.UserEntry;

@Repository("userDao")
public class UserDaoImpl extends GenericDAOImpl<UserEntry, Integer> implements UserDao{

	public UserDaoImpl() {
		super(UserEntry.class);
	}
	
	@Override
	public Integer createUser(UserEntry user) throws ClientException, Exception{
		Integer id = create(user);
		updateCache(user);
		return id;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void loadCache() throws ClientException, Exception {
		List<UserEntry> allusers = findAll();
		allusers.stream().forEach(user -> {
			try {
				updateCache(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	@Override
	public void updateCache(UserEntry user) throws ClientException, Exception {
		try (IgniteClient client = Ignition.startClient(
				new ClientConfiguration().setAddresses("127.0.0.1:10800").setUserName("ignite").setUserPassword("ignite")
				)) { 
			ClientCacheConfiguration cacheCfg = new ClientCacheConfiguration()
					.setName("user")
					.setCacheMode(CacheMode.REPLICATED)
					.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);

			ClientCache<String, String> cache = client.getOrCreateCache(cacheCfg);
			Gson gson = new Gson();

			String json = gson.toJson(user);
			cache.put(user.getEmailId(), json);

		}
	}
	
	@Override
	public String getUserByEmailid(String emailId) throws ClientException, Exception {
		try (IgniteClient client = Ignition.startClient(
				new ClientConfiguration().setAddresses("127.0.0.1:10800").setUserName("ignite").setUserPassword("ignite")
				)) { 
			ClientCacheConfiguration cacheCfg = new ClientCacheConfiguration()
					.setName("user")
					.setCacheMode(CacheMode.REPLICATED)
					.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);

			ClientCache<String, String> cache = client.getOrCreateCache(cacheCfg);
			return cache.get(emailId);

		}
	}
}

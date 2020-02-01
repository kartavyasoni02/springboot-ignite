package com.teacheron.sales.ignite;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import com.google.gson.Gson;
import com.teacheron.sales.domain.UserDomain;

public class IgniteUserCache extends IgniteConfig{
	
	public IgniteUserCache(String address, String userName, String userNamePassword) {
		this.address = address;
		this.userName = userName;
		this.userNamePassword = userNamePassword;
	}
	
	public void updateCache(UserDomain user) throws ClientException, Exception {
		try (IgniteClient client = Ignition.startClient(
				new ClientConfiguration().setAddresses(address).setUserName(userName).setUserPassword(userNamePassword)
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
	
	public String getUserByEmailid(String emailId) throws ClientException, Exception {
		
		try (IgniteClient client = Ignition.startClient(
				new ClientConfiguration().setAddresses(address).setUserName(userName).setUserPassword(userNamePassword)
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

package com.teacheron.sales.repositories.impl;

import java.util.List;

import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientCacheConfiguration;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.teacheron.sales.entities.UserEntry;
import com.teacheron.sales.repositories.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	// Spring Boot will create and configure DataSource and JdbcTemplate
	// To use it, just @Autowired
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public UserEntry createUserEntry(UserEntry userEntry) throws ClientException, Exception {
		String sql = "INSERT INTO users (firstName, lastName, address, emailId) "
				+ "VALUES (:firstName, :lastName, :address, :emailId)";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("firstName", userEntry.getFirstName());
		mapSqlParameterSource.addValue("lastName", userEntry.getLastName());
		mapSqlParameterSource.addValue("address", userEntry.getAddress());
		mapSqlParameterSource.addValue("emailId", userEntry.getEmailId());

		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
		updateCache(userEntry);
		return userEntry;


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

	@Override
	public List<UserEntry> getAllUsers() {

		String sql = "SELECT id, firstName, lastName, address, emailId FROM users";
		return namedParameterJdbcTemplate.query(sql, (rs, rowNum) ->
		new UserEntry(
				rs.getInt("id"),
				rs.getString("firstName"),
				rs.getString("lastName"),
				rs.getString("address"),
				rs.getString("emailId")
				));
	}
	
	@Override
	public void loadCache() throws ClientException, Exception {
		List<UserEntry> allusers = getAllUsers();
		allusers.stream().forEach(user -> {
			try {
				updateCache(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
}

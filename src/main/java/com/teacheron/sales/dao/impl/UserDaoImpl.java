package com.teacheron.sales.dao.impl;

import java.util.List;

import org.apache.ignite.client.ClientException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teacheron.sales.dao.UserDao;
import com.teacheron.sales.domain.UserDomain;
import com.teacheron.sales.ignite.IgniteUserCache;

@Repository("userDao")
@PropertySource(value = { "classpath:application.properties" })
public class UserDaoImpl extends GenericDAOImpl<UserDomain, Integer> implements UserDao{

	@Autowired
	private Environment environment;

	public UserDaoImpl() {
		super(UserDomain.class);
	}

	@Override
	public Integer createUser(UserDomain user) throws ClientException, Exception{
		Integer id = create(user);
		IgniteUserCache cache = getCache();
		cache.updateCache(user);
		return id;
	}

	private IgniteUserCache getCache() throws Exception {
		return new IgniteUserCache(environment.getRequiredProperty("teacheron.ignite.address"), environment.getRequiredProperty("teacheron.ignite.username"), 
				environment.getRequiredProperty("teacheron.ignite.username-password"));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public void loadCache() throws ClientException, Exception {
		
		List<UserDomain> allusers = findAll();
		allusers.stream().forEach(user -> {
			try {
				IgniteUserCache cache = getCache();
				cache.updateCache(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public UserDomain findByEmailId(String emailId) {
		return (UserDomain) getCriteria().add(Restrictions.eq("emailId", emailId)).uniqueResult();
	}

	@Override
	public String getUserByEmailid(String emailId) throws ClientException, Exception {
		IgniteUserCache cache = getCache();
		return cache.getUserByEmailid(emailId);

	}

}
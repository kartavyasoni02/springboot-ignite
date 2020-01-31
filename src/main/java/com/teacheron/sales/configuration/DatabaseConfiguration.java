package com.teacheron.sales.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class DatabaseConfiguration {

	private static final Logger logger = LogManager.getLogger(DatabaseConfiguration.class);

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT              = "teacheron.hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL             = "teacheron.hibernate.show_sql";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "teacheron.hibernate.packages.to.scan";
	
	@Autowired
	private Environment environment;
	
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put("connection.provider_class", environment.getRequiredProperty("teacheron.hibernate.hikari.type"));
		properties.put("hikari.maximumPoolSize", environment.getRequiredProperty("teacheron.hibernate.hikari.connection-timeout"));
		properties.put("hikari.connectionTimeout", environment.getRequiredProperty("teacheron.hibernate.hikari.minimum-idle"));
		properties.put("hikari.minimumIdle", environment.getRequiredProperty("teacheron.hibernate.hikari.maximum-pool-size"));
		properties.put("hikari.idleTimeout", environment.getRequiredProperty("teacheron.hibernate.hikari.idle-timeout"));
		properties.put("hikari.maxLifetime", environment.getRequiredProperty("teacheron.hibernate.hikari.max-lifetime"));
		properties.put("hikari.autoCommit", environment.getRequiredProperty("teacheron.hibernate.hikari.auto-commit"));
		return properties;
	}
	
	@ConfigurationProperties(prefix = "teacheron.datasource.hibernte")
	@Bean(name = "dataSource")
	public DataSource dataSource()throws Exception {
		logger.info("datasource loading... ");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("teacheron.datasource.hibernte.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("teacheron.datasource.hibernte.url"));
		dataSource.setUsername(environment.getRequiredProperty("teacheron.datasource.hibernte.username"));
		dataSource.setPassword(environment.getRequiredProperty("teacheron.datasource.hibernte.password"));
		logger.info("datasource loading completed ");
		return dataSource;
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws Exception {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws Exception {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan(environment.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}
}
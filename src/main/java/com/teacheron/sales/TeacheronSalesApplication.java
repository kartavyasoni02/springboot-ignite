package com.teacheron.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class TeacheronSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeacheronSalesApplication.class, args);
	}
}

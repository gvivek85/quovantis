package com.quovantis.courierapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.quovantis.controller", "com.quovantis.model", "com.quovantis.repository",
		"com.quovantis.service", "com.quovantis.scheduler", "com.quovantis.callable","com.quovantis.notification"})
@EntityScan(basePackages = {"com.quovantis.model"})
@EnableJpaRepositories(basePackages =  {"com.quovantis.repository"})
@EnableScheduling
public class CourierApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierApiApplication.class, args);
	}

}

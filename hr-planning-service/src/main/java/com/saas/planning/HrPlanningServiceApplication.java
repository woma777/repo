package com.saas.planning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.saas.planning.repository") // Adjust package if necessary
public class HrPlanningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrPlanningServiceApplication .class, args);
	}

}

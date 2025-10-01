package com.oracle.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.oracle")
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AccountServiceApplication.class);
		app.setAdditionalProfiles("account"); // This will look for application-account.properties
		app.run(args);
	}
}

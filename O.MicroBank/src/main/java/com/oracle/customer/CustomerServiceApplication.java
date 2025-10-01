package com.oracle.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.oracle")

@EnableEurekaServer
public class CustomerServiceApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "customer");
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

}



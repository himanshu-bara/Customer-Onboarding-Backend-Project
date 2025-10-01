package com.oracle.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.oracle.proxy") // Important!
public class ConsumerApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "kafka");
		SpringApplication.run(ConsumerApplication.class, args);
	}

}


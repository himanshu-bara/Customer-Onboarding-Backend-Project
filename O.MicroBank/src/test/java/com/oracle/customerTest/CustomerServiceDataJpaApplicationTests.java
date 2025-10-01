package com.oracle.customerTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oracle.customer.CustomerServiceApplication;

@SpringBootTest(classes = CustomerServiceApplication.class)
class CustomerServiceDataJpaApplicationTests {

	@Test
	void contextLoads() {
		// Just checking if Spring context loads successfully
	}
}

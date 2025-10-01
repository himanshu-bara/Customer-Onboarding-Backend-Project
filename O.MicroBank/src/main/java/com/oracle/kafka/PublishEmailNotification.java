package com.oracle.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.oracle.customer.dto.AccountEmailDTO;
import com.oracle.customer.dto.CustomerResponseDTO;
import com.oracle.customer.model.Account;
import com.oracle.customer.model.Customer;
import com.oracle.proxy.CustomerServiceProxy;



@Service
public class PublishEmailNotification {
	@Autowired
	private KafkaTemplate<String,Customer> kafkaTemplate;

//	public void sendEmailNotification(String emailMessage) {
//		kafkaTemplate.send("emailTopic",emailMessage);
//	}
	
	public void sendEmailNotification(Customer customer) {
		kafkaTemplate.send("emailTopic",customer);
	}
	


	@Autowired
	private KafkaTemplate<String, AccountEmailDTO> kafkaAccountTemplate;

	@Autowired
	private CustomerServiceProxy customerServiceProxy;

	public void sendAccountEmailNotification(Account account) {
	    CustomerResponseDTO customer = customerServiceProxy.getCustomerById(account.getCustomerId());

	    AccountEmailDTO dto = new AccountEmailDTO();
	    dto.setAccountId(account.getAccountId());
	    dto.setAccountType(account.getAccountType());
	    dto.setAccountStatus(account.getAccountStatus());

	    dto.setCustomerId(customer.getId());
	    dto.setCustomerName(customer.getFullName());
	    dto.setCustomerEmail(customer.getEmail());

	    kafkaAccountTemplate.send("accountEmailTopic", dto);
	}

}

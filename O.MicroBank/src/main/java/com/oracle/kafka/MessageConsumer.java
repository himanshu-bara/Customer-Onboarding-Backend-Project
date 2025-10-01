package com.oracle.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.oracle.customer.model.Customer;



@Service
public class MessageConsumer {

	@Autowired 	
	private JavaMailSender mailSender;
	
	@KafkaListener(topics = "emailTopic", groupId = "my-group")
	public void consumeCustomer(Customer customer) {
		String to = customer.getEmail(); 
		String subject = "New Customer Created Notification";

		String emailMessage = "";
		emailMessage += "Dear " + customer.getFullName() + ",\n";
		emailMessage += "Your account has been successfully created.\n";
		emailMessage += "Your account details are as follows:\n";
		emailMessage += "Customer ID: " + customer.getCustomerId() + "\n";
		emailMessage += "Regards,\n";
		emailMessage += "Customer Service Team";

		// Prepare the mail message
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(emailMessage);
		msg.setFrom("raspberry02pi2003@gmail.com");

		// Send the email
		mailSender.send(msg);

		System.out.println("Email sent successfully to " + to);

	}
}

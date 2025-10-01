package com.oracle.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.oracle.customer.dto.AccountEmailDTO;
import com.oracle.customer.model.Customer;

@Service
public class KafkaConsumerService {


	
	@KafkaListener(
		    topics = "accountEmailTopic",
		    groupId = "account-group",
		    containerFactory = "accountKafkaListenerContainerFactory"
		)
	public void consumeAccount(AccountEmailDTO dto) {
	    String to = dto.getCustomerEmail();
	    String subject = "Account Creation Notification";

	    String message = "Dear " + dto.getCustomerName() + ",\n\n" +
	                     "Your account has been successfully created.\n" +
	                     "Account ID: " + dto.getAccountId() + "\n" +
	                     "Account Type: " + dto.getAccountType() + "\n" +
	                     "Status: " + dto.getAccountStatus() + "\n\n" +
	                     "Regards,\nBank Team";

	    SimpleMailMessage mail = new SimpleMailMessage();
	    mail.setTo(to);
	    mail.setSubject(subject);
	    mail.setText(message);
	    mail.setFrom("raspberry02pi2003@gmail.com");

	    mailSender.send(mail);
	}


	@Autowired
	private JavaMailSender mailSender;

	@KafkaListener(
		    topics = "emailTopic",
		    groupId = "customer-group",
		    containerFactory = "customerKafkaListenerContainerFactory"
		)
	public void consume(Customer customer) {
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

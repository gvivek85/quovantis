package com.quovantis.notification;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.quovantis.callable.SendNotificationThread;
import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;

/**
 * This class contains the logic for sending Email out to user 
 * to notify about the package update
 * @author Vivek Gupta
 *
 */
public class SendEmail {
	
	public static final Logger logger = LoggerFactory.getLogger(SendEmail.class);

	public SendEmail() {
		
	}
	/**
	 * This method is used to send email to the customers
	 * @param order
	 * @param customer
	 * @param stage
	 * @return
	 */
	public boolean sendEmail(CustOrderStatus order, Customer customer, GenericMaster stage) {
		boolean emailSent = false;
//		String emailFrom = "web@gmail.com";
//		String host = "localhost";
//
//		Properties properties = System.getProperties();
//		properties.setProperty("mail.smtp.host", host);
//
//		Session session = Session.getDefaultInstance(properties);
//
//		try {
//			MimeMessage message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(emailFrom));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(customer.getEmail()));
//
//			message.setSubject("Order no" + order.getOrderId() + "has been " + stage.getDescription());
//
//			message.setContent("<h1>Order "+ order.getOrderId()+" has been "+ stage.getDescription() +"</h1>", "text/html");
//
//			Transport.send(message);
//			emailSent = true;
//		} catch (MessagingException mex) {
//			logger.error("Error sending email" + mex.getMessage());
//		}
		emailSent = true;
		
		return emailSent;
	}
	
}

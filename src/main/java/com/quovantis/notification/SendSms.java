package com.quovantis.notification;

import org.springframework.stereotype.Component;

import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;

/**
 * This class contains the logic for sending sms out to user 
 * to notify about the package update
 * @author Vivek Gupta
 *
 */
@Component
public class SendSms {

	public SendSms() {
		
	}
	/**
	 * This method is used to send the SMS to the customer
	 * @param order
	 * @param customer
	 * @param stage
	 * @return
	 */
	public boolean sendSms(CustOrderStatus order, Customer customer, GenericMaster stage) {
		boolean smsSent = false;

		// Code goes here for sending SMS
		
		
		smsSent = true;
		
		return smsSent;
	}
	
}

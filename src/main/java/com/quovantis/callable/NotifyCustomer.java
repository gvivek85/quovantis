package com.quovantis.callable;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.quovantis.constants.AppConstants;
import com.quovantis.model.ContactPreference;
import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;
import com.quovantis.notification.SendEmail;
import com.quovantis.notification.SendSms;

/**
 * This class will execute a call to send out notification based on the 
 * Customer Preference.
 * @author Vivek Gupta
 *
 */
public class NotifyCustomer implements Callable<CustOrderStatus> {

	public static final Logger logger = LoggerFactory.getLogger(SendNotificationThread.class);

	private CustOrderStatus order;

	private Customer customer;

	private Map<Long, GenericMaster> masterMap;

	public NotifyCustomer(CustOrderStatus order, Customer customer, Map<Long, GenericMaster> masterMap) {
		super();
		this.order = order;
		this.customer = customer;
		this.masterMap = masterMap;
	}

	@Override
	public CustOrderStatus call() throws Exception {
		boolean emailSent = false;
		boolean smsSent = false;

		List<ContactPreference> contPrefList = null != customer.getContactPref() ? customer.getContactPref() : null;
		if(contPrefList != null && !contPrefList.isEmpty()) {
			for(ContactPreference obj : contPrefList) {
				if(masterMap.get(obj.getPreference()).getTypeCode().equals(AppConstants.NOTIFICATION_TYPE_EMAIL)) {
					SendEmail sendEmail = new SendEmail(); 
					emailSent = sendEmail.sendEmail(order, customer, masterMap.get(order.getStageId()));
				} else if(masterMap.get(obj.getPreference()).getTypeCode().equals(AppConstants.NOTIFICATION_TYPE_SMS)) {
					SendSms sendSms = new SendSms();
					smsSent = sendSms.sendSms(order, customer, masterMap.get(order.getStageId()));
				}
			}
			if(emailSent || smsSent) {
				order.setStatus(AppConstants.COMPLETED);
			}
		} else {
			logger.error("No contact Preference set by the customer.");
			order.setStatus(AppConstants.RETRY);
		}

		return order;
	}

}

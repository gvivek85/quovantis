package com.quovantis.scheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.apache.catalina.util.CustomObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.quovantis.callable.SendNotificationThread;
import com.quovantis.constants.AppConstants;
import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;
import com.quovantis.repository.CustOrderStatusRepository;
import com.quovantis.service.OrderStatusNotifyService;

/**
 * This class is a scheduled batch job which runs every 5 min to collect data from 
 * database based on the Pending/Retry status and then filter out the latest stage data
 * to send out the notification to the user.
 * @author Vivek Gupta
 *
 */
@Component
public class NotificationScheduler {

	Logger logger = LoggerFactory.getLogger(NotificationScheduler.class); 

	@Autowired
	private OrderStatusNotifyService notifyService;

	/**
	 * This is a scheduled job that runs every 5 min and spawns a new thread to send out 
	 * the notification to the user
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0/1 * * * ?")
	public void cronNotifyScheduler() throws Exception {

		//FinalList to be sent to threads for sending out notifications
		List<CustOrderStatus> finalList = new ArrayList<CustOrderStatus>();

		//List of order for which we have recieved a latest update in DB
		List<CustOrderStatus> duplicateDataList = new ArrayList<CustOrderStatus>();

		List<CustOrderStatus> orderList = notifyService.getListByStatus(Arrays.asList(AppConstants.PENDING, AppConstants.RETRY));

		if(orderList != null && !orderList.isEmpty()) {
			orderList.forEach(item->{
				item.setStatus(AppConstants.INPROCESS);
			});

			notifyService.saveAllOrders(orderList);
		} else {
			logger.error("No Pending Notification for Order");
			return;
		}

		Comparator<CustOrderStatus> comp = Comparator.comparing(CustOrderStatus::getCustId)
				.thenComparing(CustOrderStatus::getOrderId)
				.thenComparing(CustOrderStatus::getStageId);

		Collections.sort(orderList, comp);

		Map<String, CustOrderStatus> map = new HashMap<String, CustOrderStatus>();

		for(CustOrderStatus obj1 : orderList) {
			String key = obj1.getCustId() + obj1.getOrderId();
			if(map.containsKey(key)) {
				CustOrderStatus obj2 = map.get(key);
				if(obj1.getStageId() > obj2.getStageId()) {
					map.put(key, obj1);
					duplicateDataList.add(obj2);
				}
			} else {
				map.put(key, obj1);
			}
		}

		logger.info("Final List created for Sending Notification");

		//Converting the order collection from Map to List
		map.entrySet().forEach(item->{
			finalList.add(item.getValue());
		});

		//Below are the list for fetching the CustId's and Master Id's
		List<Customer> custList = 
				notifyService.getCustomersById(finalList.stream().map(CustOrderStatus::getCustId).collect(Collectors.toList()));

		logger.info("Fetched the customer list " + custList.size());

		List<GenericMaster> masterList = 
				notifyService.getAllMasterByType(Arrays.asList(AppConstants.COURIER_STAGE, AppConstants.NOTIFICATION_TYPE));

		Map<Long, GenericMaster> masterMap = new HashMap<Long, GenericMaster>();

		Map<Long, Customer> customerMap = new HashMap<Long, Customer>();

		//Preparing the Map for customer and Generic Master for easy fetch
		masterList.forEach(item->{
			masterMap.put(item.getId(), item);
		});

		custList.forEach(item->{
			customerMap.put(item.getCustId(), item);
		});

		//Implemented a new single thread for sending out the notification
		//this will spawn a new thread instance for SendNotificationThread without impacting the 
		// main thread

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Runnable thread = new SendNotificationThread(finalList, customerMap, masterMap);
		executorService.execute(thread);
		executorService.shutdown();

		logger.info("Send Notification Batch Job successfully initiated for batch size " + finalList.size());
	}
}

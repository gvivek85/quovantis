package com.quovantis.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;
import com.quovantis.repository.CustOrderStatusRepository;
import com.quovantis.service.OrderStatusNotifyService;

/**
 * This class will run an individual thread for sending out notifications
 * @author Vivek Gupta
 *
 */
public class SendNotificationThread implements Runnable {

	public static final Logger logger = LoggerFactory.getLogger(SendNotificationThread.class);

	private List<CustOrderStatus> orderList;

	Map<Long, Customer> customerMap;

	private Map<Long, GenericMaster> masterMap;

	private int threadSize = 10;

	@Autowired
	private OrderStatusNotifyService notifyService;

	/**
	 * 
	 * @param orderList
	 * @param customerMap
	 * @param masterMap
	 */
	public SendNotificationThread(List<CustOrderStatus> orderList, Map<Long, Customer> customerMap,
			Map<Long, GenericMaster> masterMap) {
		this.orderList = orderList;
		this.customerMap = customerMap;
		this.masterMap = masterMap;
	}

	@Override
	public void run() {
		logger.info("Send Notification Thread Start");
		List<Future<CustOrderStatus>> futureList = new ArrayList<Future<CustOrderStatus>>();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadSize);

		for(CustOrderStatus order : orderList) {
			NotifyCustomer notify = new NotifyCustomer(order, customerMap.get(order.getCustId()), 
					masterMap);
			Future<CustOrderStatus> future = executor.submit(notify);

			futureList.add(future);
		}

		List<CustOrderStatus> successList = new ArrayList<CustOrderStatus>();

		for(Future<CustOrderStatus> futureObj : futureList) {
			try {
				CustOrderStatus obj = futureObj.get(30, TimeUnit.SECONDS);
				if(obj != null) {
					successList.add(obj);
				} else {
					logger.error("Notification was not Sent Successfully");
				}
			} catch (InterruptedException | ExecutionException | TimeoutException ex) {
				logger.error("::::Exception caught in Executing Thread for notification ::"+ex.getMessage() , ex);
			}
		}
		executor.shutdown();
		
		//Logic to update the status of all the future objects in batch mode
		//notifyService.saveAllOrders(successList);
		logger.info("Send Notification Thread End");
	}

}

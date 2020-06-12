package com.quovantis.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.quovantis.constants.AppConstants;
import com.quovantis.model.ApiResponse;
import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;
import com.quovantis.repository.CustOrderStatusRepository;
import com.quovantis.repository.CustomerRepository;
import com.quovantis.repository.GenericMasterRepository;

/**
 * Service Impl class that contains the methods for Courier service API
 * @author Vivek Gupta
 *
 */
@Component
@Service
public class OrderStatusNotifyServiceImpl implements OrderStatusNotifyService {

	public static final Logger logger = LoggerFactory.getLogger(OrderStatusNotifyServiceImpl.class);
	
	@Autowired
	private CustOrderStatusRepository repo;
	
	@Autowired
	private GenericMasterRepository masterRepo;
	
	@Autowired
	private CustomerRepository custRepo;
	
	/**
	 * This method saves the order updates received from various departments 
	 * within the courier company 
	 */
	@Override
	public ApiResponse saveOrderUpdates(CustOrderStatus orderStat) {
		ApiResponse resp = null;
		try {
			orderStat.setStatus(AppConstants.PENDING);
			orderStat.setCreatedAt(Instant.now());
			repo.save(orderStat);
			resp = new ApiResponse(true, AppConstants.SUCCESS_CODE, AppConstants.SUCCESS);
		} catch (Exception ex) {
			logger.error("Error while saving Order Status in DB " + ex.getMessage());
			resp = new ApiResponse(false, AppConstants.FAILURE_CODE, AppConstants.FAILURE);
		}
		return resp;
	}
	
	/**
	 * Method to get all the Masters based on Master Types
	 */
	public List<GenericMaster> getAllMasterByType(List<String> typeList){
		return masterRepo.findByType(typeList);
	}
	
	/**
	 * Fetch the list of customers based on the list of id's passed
	 */
	public List<Customer> getCustomersById(List<Long> ids){
		return custRepo.findByIds(ids);
	}

	/**
	 * Fetch the Customer Order status data from DB based on Pending/Retry status
	 */
	public List<CustOrderStatus> getListByStatus(List<String> statusList){
		return repo.getListByStatus(statusList);
	}
	
	/**
	 * Method to save a list of Customer order status 
	 * Used to update the status column, so that status can be sent just once.
	 */
	public void saveAllOrders(List<CustOrderStatus> orderList) {
		repo.saveAll(orderList);
	}
}

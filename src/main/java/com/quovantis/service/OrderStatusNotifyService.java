package com.quovantis.service;

import java.util.Arrays;
import java.util.List;

import com.quovantis.constants.AppConstants;
import com.quovantis.model.ApiResponse;
import com.quovantis.model.CustOrderStatus;
import com.quovantis.model.Customer;
import com.quovantis.model.GenericMaster;

/**
 * Service class that contains the methods for Courier service API
 * @author Vivek Gupta
 *
 */
public interface OrderStatusNotifyService {
	
	public ApiResponse saveOrderUpdates(CustOrderStatus orderStat);
	
	public List<GenericMaster> getAllMasterByType(List<String> typeList);
	
	public List<Customer> getCustomersById(List<Long> custId);
	
	public List<CustOrderStatus> getListByStatus(List<String> statusList);
	
	public void saveAllOrders(List<CustOrderStatus> orderList);
}

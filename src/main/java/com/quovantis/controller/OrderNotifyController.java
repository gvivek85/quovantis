package com.quovantis.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quovantis.model.ApiResponse;
import com.quovantis.model.CustOrderStatus;
import com.quovantis.repository.CustOrderStatusRepository;
import com.quovantis.service.OrderStatusNotifyService;

/**
 * Rest Controller Class that will be used by the other departments to post the 
 * Status update for each customer order for each stage in processing   
 * @author Vivek Gupta
 *
 */
@RestController
@RequestMapping("/order/notify")
public class OrderNotifyController {

	@Autowired
	private OrderStatusNotifyService orderStatusNotifyService;
	
	/**
	 * Post Mapping to insert a new row in database transaction table
	 * when the department moves the order package into next stage
	 * @param orderStatus
	 * @return
	 */
	@PostMapping("/saveOrderStatus")
	public ApiResponse saveOrderStatus(@RequestBody @Valid CustOrderStatus orderStatus) {
		return orderStatusNotifyService.saveOrderUpdates(orderStatus);		
	}
}

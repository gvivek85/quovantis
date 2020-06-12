package com.quovantis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quovantis.model.OrderDetails;

/**
 * Repository for OrderDetails
 * @author Vivek Gupta
 *
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

}

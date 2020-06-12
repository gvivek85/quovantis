package com.quovantis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quovantis.model.CustOrderStatus;

/**
 * Repository for transaction table for storing customer Order stage details
 * @author Vivek Gupta
 *
 */
@Repository
public interface CustOrderStatusRepository extends JpaRepository<CustOrderStatus, Long> {

	@Query(value="Select * from CUST_ORDER_STATUS orders where status in (:statusList) LIMIT 1000", nativeQuery=true )
	List<CustOrderStatus> getListByStatus(@Param("statusList") List<String> statusList);
}

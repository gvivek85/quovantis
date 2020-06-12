package com.quovantis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.quovantis.model.Customer;

/**
 * Repository for Customer
 * @author Vivek Gupta
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("from Customer cust where cust.custId in (:ids)")
	List<Customer> findByIds(@Param("ids") List<Long>ids);
}

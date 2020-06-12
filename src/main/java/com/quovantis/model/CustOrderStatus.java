package com.quovantis.model;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Class to contain the order status change transaction information
 * @author Vivek Gupta
 *
 */
@Entity
@Table(name="CUST_ORDER_STATUS",
	uniqueConstraints = {
        @UniqueConstraint( columnNames = {"cust_id", "order_id","stage_id"},
                name="cust_order_unique_constraint")
	}
)
public class CustOrderStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Cust Id is required")
	@Column(name = "cust_id")
	private Long custId;
	
	@NotEmpty(message = "Order Id is required")
	@Column(name = "order_id", length = 30)
	private String orderId;
	
	@NotNull(message = "Stage Id is required")
	@Column(name = "stage_id")
	private Long stageId;
	
	private Instant createdAt;
	private Instant updatedAt;
	private String status;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the custId
	 */
	public Long getCustId() {
		return custId;
	}
	/**
	 * @param custId the custId to set
	 */
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the stageId
	 */
	public Long getStageId() {
		return stageId;
	}
	/**
	 * @param stageId the stageId to set
	 */
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	/**
	 * @return the createdAt
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * @return the updatedAt
	 */
	public Instant getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}

package com.quovantis.model;

/**
 * Generic Api Response class. This class will be used to send back the response 
 * for each API Hit
 * @author Vivek Gupta
 *
 */
public class ApiResponse {
	private Boolean success;
	private Long responseCode;
	private String responseMsg;
	
	public ApiResponse() {
	
	}
	
	public ApiResponse(Boolean success, Long responseCode, String responseMsg) {
		super();
		this.success = success;
		this.responseCode = responseCode;
		this.responseMsg = responseMsg;
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the responseCode
	 */
	public Long getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(Long responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}

	/**
	 * @param responseMsg the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
}

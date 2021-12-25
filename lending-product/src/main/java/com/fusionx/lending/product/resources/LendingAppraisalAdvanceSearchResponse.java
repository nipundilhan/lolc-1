package com.fusionx.lending.product.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Lending Account Detail advance search Resource
 *
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-10-2021      	-          -	     Thusahn        Created
 *
 ********************************************************************************************************
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LendingAppraisalAdvanceSearchResponse {

    private String id;
    private String customerName;
    private String customerCode;
    private String customerId;
    private String customerBrNumber;
    private String leadNumber;
    private String subProduct;
    private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerBrNumber() {
		return customerBrNumber;
	}
	public void setCustomerBrNumber(String customerBrNumber) {
		this.customerBrNumber = customerBrNumber;
	}
	public String getLeadNumber() {
		return leadNumber;
	}
	public void setLeadNumber(String leadNumber) {
		this.leadNumber = leadNumber;
	}
	public String getSubProduct() {
		return subProduct;
	}
	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    

}

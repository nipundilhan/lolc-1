package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.EligibilityPending;
import com.fusionx.lending.product.domain.SubProduct;
import com.fusionx.lending.product.domain.SubProductPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.SubProductAddResource;
import com.fusionx.lending.product.resources.SubProductLoanApplicableUpdateResource;
import com.fusionx.lending.product.resources.SubProductUpdateResource;

/**
 * Sub Product Service 
 * @author 	Sanatha
 * @Date    19-JUL-2021
 * 
 ********************************************************************************************************
 *  ###   	Date         	Story Point   	Task No    		Author      	 Description
 *-------------------------------------------------------------------------------------------------------
 *    1   	19-JUL-2021  	FXL-25      	FXL-25   		Sanatha     	 Initial Development.
 *    
 ********************************************************************************************************
 */
@Service
public interface SubProductService {
	
	/**
	 * 
	 * @apiNote Find all SubProduct 
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @return -JSON array of all SubProduct 
	 * 
	 * */
	public List<SubProduct> getAll();
	
	/**
	 * 
	 * @apiNoteFind SubProduct  by ID
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @return -JSON array of SubProduct 
	 * 
	 * */
	public Optional<SubProduct> getById(Long id);
	
	/**
	 * 
	 * @apiNote Find SubProduct by Status
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @return -JSON array of all SubProduct 
	 * 
	 * */
	public List<SubProduct> getByStatus(String status);
	
	/**
	 * 
	 * @apiNote Find SubProduct by Name
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @return -JSON array of all SubProduct 
	 * 
	 * */
	public List<SubProduct> getByName(String name);
	
	/**
	 * 
	 * @apiNoteFind SubProduct  by Code
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @return -JSON array of SubProduct 
	 * 
	 * */
	public Optional<SubProduct> getByCode(String code);
	
	/**
	 * 
	 * @apiNote Insert SubProduct 
	 * @author Sanatha
	 * @param  - SubProductAddResource
	 * @return - Successfully saved
	 * @since 19-JUL-2021 
	 * 
	 * */
	public SubProduct addSubProduct(String tenantId , SubProductAddResource subProductAddResource);
	
	/**
	 * 
	 * @apiNote Update SubProduct 
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param  - SubProductUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public SubProduct updateSubProduct(String tenantId, Long id, SubProductUpdateResource subProductUpdateResource);
	
	/**
	 * 
	 * @apiNote Approve Reject
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param  - tenantId
	 * @param - workflowStatus
	 * @return - Successfully saved
	 * 
	 * */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	/**
	 * 
	 * @apiNote Find Pending SubProduct  by ID
	 * @author Sanatha
	 * @since 19-JUL-2021 
	 * @param pendingId
	 * @return -JSON object of SubProduct  Pending
	 * 
	 * */
	public Optional<SubProductPending> getByPendingId(Long pendingId);
	
	/**
	 * 
	 * @apiNote map loan applicable range
	 * @author Nipun Dilhan
	 * @since 14-SEP-2021 
	 * @param sub product id
	 * @return -JSON object of SubProduct
	 * 
	 * */
	SubProduct updateSubProductLoanApplicableId(Long id ,SubProductLoanApplicableUpdateResource subProductLoanApplicableUpdateResource);

}

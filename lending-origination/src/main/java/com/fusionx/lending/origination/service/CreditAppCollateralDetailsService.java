package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.CreditAppCollateralDetail;
import com.fusionx.lending.origination.enums.CollateralType;
import com.fusionx.lending.origination.resource.ApprovalCatProductMapUpdateResource;
import com.fusionx.lending.origination.resource.CreAppCollateralIntergrateResource;
import com.fusionx.lending.origination.resource.CreditAppraiselCollateralDetailsAddResource;
import com.fusionx.lending.origination.resource.CreditAppraiselCollateralDetailsUpdateResource;

/**
 * Credit App Collateral Details Service
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		            	VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Service
public interface CreditAppCollateralDetailsService {

	/**
	 * Find all.
	 *
	 * @param pageable - the pageable
	 * @return the page
	 */
	public Page<CreditAppCollateralDetail> findAll(Pageable pageable);

	/**
	 * Find by id.
	 *
	 * @param id - the id
	 * @param tenantId 
	 * @return the BusinessType
	 */
	public Optional<CreditAppCollateralDetail> findById(Long id, String tenantId);
	
	
	/**
	 * Find by status.
	 *
	 * @param status - the status
	 * @param tenantId 
	 * @return the ApprovalCategory
	 */
	
	public List<CreditAppCollateralDetail> findByStatus(String status, String tenantId);

	
	/**
	 * Exists by id.
	 *
	 * @param id - the id
	 * @return the boolean
	 */
	public Boolean existsById(Long id);
	
	
	/**
	 * Save and validate ApprovalCategory.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param CreditAppraiselCollateralDetailsAddResource - CreditAppraiselCollateralDetails add resource
	 * @return the id
	 */
	//public Boolean saveAndValidateCreditAppCollateralDetails(String tenantId, String createdUser, List<CreditAppraiselCollateralDetailsAddResource> creditAppraiselCollateralDetailsAddResource);
	public Boolean saveAndValidateCreditAppCollateralDetails(String tenantId, String createdUser,
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetails);
	
	/**
	 * Update and validate business type.
	 *
	 * @param tenantId - the tenant id
	 * @param createdUser - the created user
	 * @param id - the id
	 * @param CreditAppraiselCollateralDetailsUpdateResource - CreditAppraiselCollateralDetails update resource
	 * @return the id
	 */
	public Long updateAndValidateCreditAppCollateralDetails(String tenantId, String createdUser, Long id,
			CreditAppraiselCollateralDetailsUpdateResource creditAppraiselCollateralDetailsUpdateResource);


	//public List<CreditAppCollateralDetail> findAll();
	
	
	//public List<CreditAppCollateralDetail> findByCustomerIdAndCollateralType(Long customerId, CollateralType collateralType);

	public void intergrateAndSaveNewColateralDetails(String tenantId, String userName, @Valid CreAppCollateralIntergrateResource creAppCollateralIntergrateResource);

	public List<CreditAppCollateralDetail> getCreditAppCollateralDetails(Long customerId, String tenantId);

	public List<CreditAppCollateralDetail> findAll(String tenantId);

	public CreditAppCollateralDetail addCreditAppCollateralDetail(String tenantId,
			CreditAppraiselCollateralDetailsAddResource creditAppraiselCollateralDetailsAddResource);

	


}

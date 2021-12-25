package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.CoreProduct;
import com.fusionx.lending.product.domain.CoreProductPending;
import com.fusionx.lending.product.enums.WorkflowStatus;
import com.fusionx.lending.product.resources.CoreProductAddResource;
import com.fusionx.lending.product.resources.CoreProductUpdateResource;
/**
 * API Service related to  Core Product
 * @author Dilhan
 * @version 1.0.0
 * @since 1.0.0
 * <p>
 * <br/><br/>
 * <b>Change History : </b>
 * <br/><br/>
 * #        Date            Story Point     Task No     Author                  Description
 * <br/>
 * .....................................................................................................................<br/>
 * 1        23-09-2021      -               FXL-795     Dilhan                   Created
 * <p>
 */
@Service
public interface CoreProductService {

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	List<CoreProduct> findAll();
	
	
	/**
	 * Find by id.
	 *
	 * @param coreProductId the core product id
	 * @return the optional
	 */
	public Optional<CoreProduct> findById(Long coreProductId);
	
	
	/**
	 * Find by status.
	 *
	 * @param status the status
	 * @return the list
	 */
	public List<CoreProduct> findByStatus(String status);
	
	
	/**
	 * Adds the core product.
	 *
	 * @param tenantId the tenant id
	 * @param coreProductAddResource the core product add resource
	 * @return the core product
	 */
	public CoreProduct addCoreProduct(String tenantId, CoreProductAddResource coreProductAddResource);
	
	
	/**
	 * Update core product.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param coreProductUpdateResource the core product update resource
	 * @return the core product
	 */
	public CoreProduct updateCoreProduct(String tenantId, Long id, CoreProductUpdateResource coreProductUpdateResource);
	
	
	/**
	 * Gets the core product by code.
	 *
	 * @param coreProductCode the core product code
	 * @return the core product by code
	 */
	public Optional<CoreProduct> getCoreProductByCode(String coreProductCode);
	
	
	/**
	 * Approve reject.
	 *
	 * @param tenantId the tenant id
	 * @param id the id
	 * @param workflowStatus the workflow status
	 * @return true, if successful
	 */
	public boolean approveReject(String tenantId, Long id, WorkflowStatus workflowStatus);
	
	
	/**
	 * Gets the by pending id.
	 *
	 * @param pendingId the pending id
	 * @return the by pending id
	 */
	public Optional<CoreProductPending> getByPendingId(Long pendingId);
	
	/**
	 * Gets the by description.
	 *
	 * @param desc the desc
	 * @return the by description
	 */
	public List<CoreProduct> getByDescription(String desc);
	
	/**
	 * Search Core Product Pending
	 *
	 * @param searchq - the searchq
	 * @param status - the status
	 * @param approveStatus - the approve status
	 * @param pageable - the pageable
	 * @return the page
	 */
	Page<CoreProductPending> searchCoreProduct(String searchq, String status, String approveStatus, Pageable pageable);

}

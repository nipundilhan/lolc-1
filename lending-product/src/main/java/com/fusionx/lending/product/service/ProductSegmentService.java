package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import com.fusionx.lending.product.domain.ProductSegment;
import com.fusionx.lending.product.resources.AddProductSegmentRequest;
import com.fusionx.lending.product.resources.UpdateProductSegmentRequest;

public interface ProductSegmentService {

	 /**
     * Get all Product Segment details
     * @author Venuki
     * 
     * @return    - JSON Array of all ProductSegment objects
     * 
     */
	public List<ProductSegment> findAll();
	
	/**
     * Find all ProductSegment By Product Id
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of all ProductSegment objects
	 * 
	 */
	public List<ProductSegment> findByProductId(Long productId);
	
	
	/**
     * Get ProductSegment by passing status
     * @author Venuki
     * 
     * @param status   - status
     * @return         - JSON Array of ProductSegment related status
     * 
     */
	
	public List<ProductSegment> findByStatus(String status);
	
	/**
     * Get ProductSegment passing by  id
     * @author Venuki
     * 
     * @param id   		  -  Id
     * @return            - JSON Array of ProductSegment related  id
     * 
     */
	
	public Optional<ProductSegment> findById(long id);
	
	/**
     * Insert ProductSegment details 
     * @author Venuki
     * 
     * @param AddProductSegmentRequest  - All column detail of new AddProductSegmentRequest type
     * @return      - Success message.
     * 
     */
	public 	ProductSegment addProductSegment(String tenantId, String createdUser,
			AddProductSegmentRequest addProductSegmentRequest);
	
	 /**
     * Update ProductSegment Details
     * @author Venuki
     * 
     * @param cmls     - UpdateProductSegmentRequest column detail for Update 
     * @return         - Success message.
     * 
     */
	public ProductSegment updateProductSegment(Long id, String modifiedUser, String tenantId,UpdateProductSegmentRequest updateProductSegmentRequest);
	
	 
	 /**
     * Check whether there exists desired ProductSegment object 
     * @author Venuki
     * 
     * @param id   - Id
     * @return     - Boolean value
     * 
     */
	public boolean exists(long id);


;


	


}

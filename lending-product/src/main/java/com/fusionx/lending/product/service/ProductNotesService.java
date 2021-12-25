package com.fusionx.lending.product.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.fusionx.lending.product.domain.ProductNotes;
import com.fusionx.lending.product.resources.AddNotesRequest;
import com.fusionx.lending.product.resources.UpdateNotesRequest;

public interface ProductNotesService {

	 /**
     * Get all Product Notes details
     * @author Venuki
     * 
     * @return    - JSON Array of all ProductNotes objects
     * 
     */
	public Optional<Collection<ProductNotes>> findAll();
	
	
	/**
     * Get ProductNotes passing by  id
     * @author Venuki
     * 
     * @param id   		  -  Id
     * @return            - JSON Array of ProductNotes related  id
     * 
     */
	
	public Optional<ProductNotes> findById(long id);
	
	/**
     * Find all ProductNotes Information By product Id
	 * @author Venuki
	 * 
	 * @return   		- JSON Array of all ProductNotes objects
	 * 
	 */
	public List<ProductNotes> findByProductId(Long productId);
	
	/**
     * Insert ProductNotes details 
     * @author Venuki
     * 
     * @param NotesRequest  - All column detail of new NotesRequest type
     * @return      - Success message.
     * 
     */
	public ProductNotes addProductNotes(String tenantId, String productId,AddNotesRequest addNotesRequest, String username);
	

	/**
     * Update ProductNotes details 
     * @author Venuki
     * 
     * @param NotesRequest  - All column detail of new UpdateNotesRequest type
     * @return      - Success message.
     * 
     */
	public ProductNotes updateProductNotes(String tenantId, UpdateNotesRequest updateNotesRequest, Long id, String username);

	
	public boolean exists(long id);

	


}

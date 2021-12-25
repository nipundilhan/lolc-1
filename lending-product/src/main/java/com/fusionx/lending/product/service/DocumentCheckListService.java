package com.fusionx.lending.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.product.domain.AccountStatus;
import com.fusionx.lending.product.domain.DocumentCheckList;
import com.fusionx.lending.product.domain.DocumentCheckListDetails;
import com.fusionx.lending.product.resources.DocumentCheckListAddResource;
import com.fusionx.lending.product.resources.DocumentCheckListUpdateResource;

@Service
public interface DocumentCheckListService {
	
	/**
	 * 
	 * Find all Document Check List
	 * @author Dilhan
	 * @return -JSON array of all Expence Type Cultivation Category
	 * 
	 * */
	public List<DocumentCheckList> getAll();
	
	/**
	 * 
	 * Find Document Check List by ID
	 * @author MenukaJ
	 * @return -JSON array of Document Check List
	 * 
	 * */
	public Optional<DocumentCheckList> getById(Long id);
	
	/**
	 * 
	 * Find Document Check List by status
	 * @author Dilhan
	 * @return -JSON array of Document Check List
	 * 
	 * */
	public List<DocumentCheckList> getByStatus(String status);
	
	/**
	 * 
	 * Find Document Check List by code
	 * @author Dilhan
	 * @return -JSON array of Document Check List
	 * 
	 * */
	public Optional<DocumentCheckList> getByCode(String code);
	
	/**
	 * 
	 * Insert Document Check List
	 * @author Dilhan
	 * @param  - DocumentCheckListAddResource
	 * @return - Successfully saved
	 * 
	 * */
	public DocumentCheckList addDocumentCheckList(String tenantId , DocumentCheckListAddResource documentCheckListAddResource);

	/**
	 * 
	 * Update Document Check List
	 * @author Dilhan
	 * @param  - DocumentCheckListUpdateResource
	 * @return - Successfully saved
	 * 
	 * */
	public DocumentCheckList updateDocumentCheckList(String tenantId, Long id, DocumentCheckListUpdateResource documentCheckListUpdateResource);


	public List<DocumentCheckList> findByProductName(String name);
	
	public List<DocumentCheckList> findBySubProductName(String name);
	
	public List<DocumentCheckList> findByName(String name);
	
	Optional<DocumentCheckListDetails> findById(Long id);
}

package com.fusionx.lending.origination.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fusionx.lending.origination.domain.DocumentCheckList;
import com.fusionx.lending.origination.resource.DocumentCheckListAddResource;
import com.fusionx.lending.origination.resource.DocumentCheckListUpdateResource;

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


}

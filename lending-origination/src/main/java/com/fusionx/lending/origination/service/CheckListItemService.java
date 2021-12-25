package com.fusionx.lending.origination.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
/**
 * Check List Item
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   16-08-2021   FXL-75  		 FXL-550 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.CheckListItem;
import com.fusionx.lending.origination.resource.CheckListItemAddResource;
import com.fusionx.lending.origination.resource.CheckListItemUpdateResource;

@Service
public interface CheckListItemService {

	/**
	 * 
	 * Find all CheckListItem
	 * 
	 * @author Dilki
	 * @return JSON array of all CheckListItem
	 * 
	 */
	public List<CheckListItem> getAll();

	/**
	 * 
	 * Find CheckListItem by ID
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListItem
	 * 
	 */
	public Optional<CheckListItem> getById(Long id);

	/**
	 * 
	 * Find CheckListItem by code
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListItem
	 * 
	 */
	public Optional<CheckListItem> getByCode(String code);

	/**
	 * 
	 * Find CheckListItem by name
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListItem
	 * 
	 */
	public List<CheckListItem> getByName(String name);

	/**
	 * 
	 * Find CheckListItem by status
	 * 
	 * @author Dilki
	 * @return JSON array of CheckListItem
	 * 
	 */
	public List<CheckListItem> getByStatus(String status);

	/**
	 * 
	 * Insert CheckListItem
	 * 
	 * @author Dilki
	 * @param CheckListItemAddResource
	 * @return Successfully saved
	 * 
	 */
	public CheckListItem addCheckListItem(String tenantId, CheckListItemAddResource checkListItemAddResource);

	/**
	 * 
	 * Update CheckListItem
	 * 
	 * @author Dilki
	 * @param CheckListItemUpdateResource
	 * @return Successfully updated
	 * 
	 */
	public CheckListItem updateCheckListItem(String tenantId, Long id,
			CheckListItemUpdateResource checkListItemUpdateResource);
}

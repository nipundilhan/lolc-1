package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CheckListItem;
import com.fusionx.lending.origination.enums.CommonStatus;
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
@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, Long> {

	public List<CheckListItem> findByStatus(CommonStatus status);

	public List<CheckListItem> findByNameContaining(String name);

	public Optional<CheckListItem> findByCode(String code);

	public Optional<CheckListItem> findByCodeAndStatus(String code, CommonStatus status);
	
	public Optional<CheckListItem> findByIdAndStatus(Long id, CommonStatus status);

}

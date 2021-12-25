package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Approval Category Levels
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   14-09-2021    		 	 FXL-823 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryLevels;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ApprovalCategoryLevelsRepository extends JpaRepository<ApprovalCategoryLevels, Long> {

	public List<ApprovalCategoryLevels> findByStatus(CommonStatus status);

	public List<ApprovalCategoryLevels> findByNameContaining(String name);

	public Optional<ApprovalCategoryLevels> findByCode(String code);

}

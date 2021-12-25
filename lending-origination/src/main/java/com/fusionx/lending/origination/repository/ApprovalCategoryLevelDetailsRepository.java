package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Approval Category Level Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-09-2021    		 	 FXL-840 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryLevelDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ApprovalCategoryLevelDetailsRepository extends JpaRepository<ApprovalCategoryLevelDetails, Long> {

	public List<ApprovalCategoryLevelDetails> findByStatus(CommonStatus status);

	public List<ApprovalCategoryLevelDetails> findByNameContaining(String name);

	public Optional<ApprovalCategoryLevelDetails> findByCode(String code);

	public List<ApprovalCategoryLevelDetails> findByApprovalCategoryDetailsId(Long id);
}

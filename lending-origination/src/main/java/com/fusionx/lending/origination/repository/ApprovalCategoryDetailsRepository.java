package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Approval Category Details
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   13-09-2021   FXL-338 		 FXL-803 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import com.fusionx.lending.origination.domain.ApprovalCategoryDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ApprovalCategoryDetailsRepository extends JpaRepository<ApprovalCategoryDetails, Long> {

	public List<ApprovalCategoryDetails> findByStatus(CommonStatus status);

	public List<ApprovalCategoryDetails> findByNameContaining(String name);

	public Optional<ApprovalCategoryDetails> findByCode(String code);
}
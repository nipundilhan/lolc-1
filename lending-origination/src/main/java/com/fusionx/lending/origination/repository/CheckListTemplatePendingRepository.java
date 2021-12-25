package com.fusionx.lending.origination.repository;
/**
 * Check List Template
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   17-08-2021   FXL-75  		 FXL-551 	Dilki        Created
 *    
 ********************************************************************************************************
 */
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CheckListTemplatePending;
import com.fusionx.lending.origination.enums.CommonApproveStatus;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface CheckListTemplatePendingRepository extends JpaRepository<CheckListTemplatePending, Long> {

	public Optional<CheckListTemplatePending> findByCheckListTemplateIdAndStatusAndApproveStatus(Long id,
			CommonStatus status, CommonApproveStatus approveStatus);

}

package com.fusionx.lending.origination.repository;

import java.util.List;

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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.CheckListTemplateDetails;

@Repository
public interface CheckListTemplateDetailsRepository extends JpaRepository<CheckListTemplateDetails, Long> {

	List<CheckListTemplateDetails> findByCheckListTemplateId(Long checkListTemplateId);

}

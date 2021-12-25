package com.fusionx.lending.product.repository;

import com.fusionx.lending.product.domain.DueDateDetails;
import com.fusionx.lending.product.domain.DueDateTemplates;
import com.fusionx.lending.product.enums.CommonStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Due Date Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   23-09-2021    FXL-139  	 FXL-926	Piyumi      Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface DueDateDetailsRepository extends JpaRepository<DueDateDetails, Long> {

	List<DueDateDetails> findByDueDateTmp(DueDateTemplates dueDateTemplate);

	List<DueDateDetails> findByStatus(CommonStatus status);


}

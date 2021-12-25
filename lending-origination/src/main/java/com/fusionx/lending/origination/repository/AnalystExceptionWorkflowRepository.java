package com.fusionx.lending.origination.repository;
/**
 * 	Analyst Exception Workflow Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No       Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   26-08-2021   FXL-117  	 FXL-543       Piyumi     Created
 *    
 ********************************************************************************************************
*/


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.AnalystExceptionWorkflow;
import com.fusionx.lending.origination.enums.WorkflowStatus;



@Repository
public interface AnalystExceptionWorkflowRepository extends JpaRepository<AnalystExceptionWorkflow, Long>{
	
	Optional<AnalystExceptionWorkflow> findByAnalystExceptionDetailIdAndWorkflowStatus(Long analystExceptionDetailId, WorkflowStatus status);
	
	Optional<AnalystExceptionWorkflow> findByAnalystExceptionDetailIdAndWorkflowProcessId(Long analystExceptionDetailId, Long workflowProcessId);
	
}

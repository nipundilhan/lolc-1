package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.LeadApprovalDetail;


/**
 * Account Status Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   10-12-2021      		     	        Achini        Created
 *    
 ********************************************************************************************************
 */
@Repository
public interface LeadApprovalDetailRepository extends JpaRepository<LeadApprovalDetail, Long>{

	public List<LeadApprovalDetail> findByLeadId(Long leadId);
	
	
}

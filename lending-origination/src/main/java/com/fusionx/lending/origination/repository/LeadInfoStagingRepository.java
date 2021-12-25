package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.LeadInfoStaging;
import com.fusionx.lending.origination.resource.LeadInfoStagingWithAdditionalDetailsResource;

/**
 * Lead Info Staging Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   24-JUN-2021   		      FX-6676    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface LeadInfoStagingRepository extends JpaRepository<LeadInfoStaging, Long>{
	
	public List<LeadInfoStaging> findByStatus(String status);
	
	public List<LeadInfoStaging> findByLeadInfoId(Long id);
	
	public List<LeadInfoStaging> findByStagingStatus(String stagingStatus);
	
	@Query(" SELECT new com.fusionx.lending.origination.resource.LeadInfoStagingWithAdditionalDetailsResource(c.id, "
			   + " a.stagingStatus, "
			   + " b.customerType, "
		       + " b.fullName, "
		       + " b.cusReferenceCode, "
		       + " d.identificationType,"
		       + " d.identificationNo,"
		       + " b.brNumber, "
		       + " b.companyName, "
		       + " a.createdUser,"
		       + " a.id,"
		       + " a.createdDate,"
		       + " a.modifiedUser,"
		       + " a.modifiedDate,"
		       + " a.version,"
		       + " a.comment,"
		       + " a.tenantId,"
		       + " a.syncTs,"
		       + " a.status,"
		       + " b.id ) "
		  + " from Customer b left join IdentificationDetail d on d.customer.id = b.id"
		  + ", LeadInfo c  "
		  + " , LeadInfoStaging  a  "
		+ " where b.customerMainType = 'MAIN' "
	+ " and  a.stagingStatus = :stagingStatus "
	+ " and b.lead.id = c.id"
	+ " and a.leadInfo.id = c.id"
	+ " and  a.createdUser = :user ")
	public List<LeadInfoStagingWithAdditionalDetailsResource> leadInfoStagingWithAdditionalDetails(@Param("stagingStatus")String stagingStatus, @Param("user")String user);
	
	//join LeadInfo c on b.leadInfo.id = c.id
	
	/*
	
	,"
		       + " a.id,"
		       + " a.createdDate,"
		       + " a.modifiedUser,"
		       + " a.modifiedDate,"
		       + " a.version,"
		       + " a.comment,"
		       + " a.tenantId,"
		       + " a.syncTs,"
		       + " a.status,"
		       + " b.id
	 */

}

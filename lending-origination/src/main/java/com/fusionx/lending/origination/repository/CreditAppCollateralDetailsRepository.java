package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ApprovalCategory;
import com.fusionx.lending.origination.domain.ApprovalCategoryProductMapping;
import com.fusionx.lending.origination.domain.CreditAppCollateralDetail;
import com.fusionx.lending.origination.enums.CollateralType;
import com.fusionx.lending.origination.enums.CommonStatus;


/**
 * Credit Appraisel Collateral Details Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   20-04-2021      		     	        VenukiR      Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface CreditAppCollateralDetailsRepository extends JpaRepository<CreditAppCollateralDetail, Long> {

	public Page<CreditAppCollateralDetail> findAll(Pageable pageable);

	public List<CreditAppCollateralDetail> findByStatus(CommonStatus status);

	public Optional<CreditAppCollateralDetail> findByIdAndStatus(Long id, String status);
	
	public List<CreditAppCollateralDetail> findByCustomerIdAndCollateralTypeAndStatus(Long customerId,String colType, String status);

	public List<CreditAppCollateralDetail> findByCustomerIdAndCollateralTypeAndStatus(Long customerId, CollateralType collateralType,
			CommonStatus active);

	public List<CreditAppCollateralDetail> findByCustomerId(Long customerId);
	
	public Optional<CreditAppCollateralDetail> findByCustomerIdAndAssetEntityId(Long customerId, Long colRefId);



}
package com.fusionx.lending.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.PenalInterestTypeDetails;

/**
* Document Check list Details Repository
* 
********************************************************************************************************
*  ###   Date         Story Point   Task No    Author       Description
*-------------------------------------------------------------------------------------------------------
*    1   15-08-2021      		            	Dilhan     Created
*    
********************************************************************************************************
*/
@Repository
public interface PenalInterestTypeDetailsRepository extends JpaRepository<PenalInterestTypeDetails, Long>{

	List<PenalInterestTypeDetails> findByPenalInterestTypeId(Long id);
}

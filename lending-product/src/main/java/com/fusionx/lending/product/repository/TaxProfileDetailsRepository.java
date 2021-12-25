package com.fusionx.lending.product.repository;

import java.util.List;

/**
 * Tax Profile Service
 * @author 	KilasiG
 * @Date     05-11-2019
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   05-11-2019   FX-1545       FX-2175    KilasiG      Created
 *    
 ********************************************************************************************************
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.TaxProfileDetails;

@Repository
public interface TaxProfileDetailsRepository extends JpaRepository<TaxProfileDetails, Long> {
	
	List<TaxProfileDetails> findAllByTaxProfileIdId(Long taxProfileId);

}

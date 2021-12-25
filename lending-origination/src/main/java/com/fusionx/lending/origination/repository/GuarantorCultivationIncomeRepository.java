package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.GuarantorIncomeCultivation;

/**
 * Guarantor Cultivation Income Service.
 * 
 ********************************************************************************************************
 *  ###   Date         	Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   04-MAY-2021   		      FX-6301    Sanatha      Initial Development.
 *    
 ********************************************************************************************************
 */
@Repository
public interface GuarantorCultivationIncomeRepository extends JpaRepository<GuarantorIncomeCultivation, Long>{
	
	public List<GuarantorIncomeCultivation> findByGuarantorIdAndStatus(Long guarantorId, String status);
	
	public List<GuarantorIncomeCultivation> findByGuarantorIdAndCultivationCategoryId(Long guarantorId, Long cultivationCategoryId);
	
	public List<GuarantorIncomeCultivation> findByGuarantorIdAndCultivationCategoryIdAndIdNotIn(Long guarantorId, Long cultivationCategoryId, Long id);

}

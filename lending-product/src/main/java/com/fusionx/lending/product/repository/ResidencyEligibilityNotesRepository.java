package com.fusionx.lending.product.repository;

/**
 * Residency Eligibility Notes service
 * @author 	Rangana
 * @Dat     30-06-2021
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   30-06-2021   FX-6       FX-6819     Rangana      Created
 *    
 ********************************************************************************************************
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.product.domain.ResidencyEligibilityNotes;


@Repository
public interface ResidencyEligibilityNotesRepository extends JpaRepository<ResidencyEligibilityNotes, Long> {
	
	List<ResidencyEligibilityNotes> findByStatus(String status);
	
	List<ResidencyEligibilityNotes> findByResidencyEligiId(Long residencyEligibilityId);

}

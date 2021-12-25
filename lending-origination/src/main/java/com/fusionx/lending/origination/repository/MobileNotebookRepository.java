package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.MobileNotebook;
import com.fusionx.lending.origination.enums.MobileNotebookStatusEnum;
import com.fusionx.lending.origination.resource.MobileNotebookReminderResponseResource;

/**
 * Mobile Notebook Repository
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-06-2021   		         FX-6506    SenithaP     Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface MobileNotebookRepository extends JpaRepository<MobileNotebook, Long> {
	
	List<MobileNotebook> findByCustomerFullNameContaining(String customerFullName);
	
	List<MobileNotebook> findByNicNoContaining(String nicNo);
	
	Optional<MobileNotebook> findByNicNoAndOnboardingStatusAndIdNotIn(String nicNo, MobileNotebookStatusEnum onboardingStatus, Long id);
	
	Optional<MobileNotebook> findByNicNoAndOnboardingStatus(String nicNo, MobileNotebookStatusEnum onboardingStatus);

	List<MobileNotebook> findByStatus(MobileNotebookStatusEnum status);

	List<MobileNotebook> findByOnboardingStatus(MobileNotebookStatusEnum onboardingStatus);
	
	@Query(value = " SELECT c FROM MobileNotebook c WHERE c.nextMeetingDate = TO_DATE(:formatedSysDate, 'YYYY-MM-DD') ")
	List<MobileNotebookReminderResponseResource> findBySystemDate(@Param("formatedSysDate")String formatedSysDate);

}

package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fusionx.lending.origination.domain.ApprovalGroup;
import com.fusionx.lending.origination.enums.CommonStatus;
/**
 * Approval Group
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   27-09-2021   FXL-78 		 FXL-977 	Dilki        Created
 *    
 ********************************************************************************************************
 */

@Repository
public interface ApprovalGroupRepository extends JpaRepository<ApprovalGroup, Long> {

	public List<ApprovalGroup> findByStatus(CommonStatus status);

	public List<ApprovalGroup> findByNameContaining(String name);

	public Optional<ApprovalGroup> findByCode(String code);

}

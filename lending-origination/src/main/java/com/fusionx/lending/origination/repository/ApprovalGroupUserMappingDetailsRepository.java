package com.fusionx.lending.origination.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ApprovalGroupUserMappingDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface ApprovalGroupUserMappingDetailsRepository
		extends JpaRepository<ApprovalGroupUserMappingDetails, Long> {

	public List<ApprovalGroupUserMappingDetails> findByStatus(CommonStatus status);

	public List<ApprovalGroupUserMappingDetails> findByApprovalGroupId(Long approvalGroupId);

}

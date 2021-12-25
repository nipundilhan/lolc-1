package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.UserMappingDetails;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface UserMappingDetailsRepository extends JpaRepository<UserMappingDetails, Long> {

	public List<UserMappingDetails> findByStatus(CommonStatus status);

	public List<UserMappingDetails> findByApprovalGroupId(Long approvalGroupId);

	public Optional<UserMappingDetails> findByUserIdAndApprovalGroupIdAndStatus(Long authorityId, Long approvalGroupId,
			CommonStatus status);
	
	public Boolean existsByUserIdAndName(Long userId, String userName);

}

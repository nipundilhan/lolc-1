package com.fusionx.lending.transaction.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.WaiveOffApprovalUsers;
import com.fusionx.lending.transaction.enums.CommonStatus;

@Repository
public interface WaiveOffApprovalUsersRepository extends JpaRepository<WaiveOffApprovalUsers, Long> {
	
	List<WaiveOffApprovalUsers> findByStatus(CommonStatus status);
	
	List<WaiveOffApprovalUsers> findByUserId(String userId);
	
	List<WaiveOffApprovalUsers> findByWaiveOffApprovalGroupId(Long waiveOffApprovalGroupId);
	
	Optional<WaiveOffApprovalUsers> findByWaiveOffApprovalGroupIdAndUserIdAndStatus(Long waiveOffApprovalGroupId, String userId, CommonStatus status);
	
	Optional<WaiveOffApprovalUsers> findByWaiveOffApprovalGroupIdAndUserIdAndStatusAndIdNotIn(Long waiveOffApprovalGroupId, String userId, CommonStatus status, Long id);
	
}

package com.fusionx.lending.transaction.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.WaiveOffApprovalTypes;
import com.fusionx.lending.transaction.enums.CommonStatus;

@Repository
public interface WaiveOffApprovalTypesRepository extends JpaRepository<WaiveOffApprovalTypes, Long> {
	
	List<WaiveOffApprovalTypes> findByStatus(CommonStatus status);
	
	List<WaiveOffApprovalTypes> findByWaiveOffTypeId(Long waiveOffTypeId);
	
	List<WaiveOffApprovalTypes> findByWaiveOffApprovalGroupId(Long waiveOffApprovalGroupId);
	
	Optional<WaiveOffApprovalTypes> findByWaiveOffTypeIdAndWaiveOffApprovalGroupIdAndStatusAndIdNotIn(Long waiveOffTypeId, Long waiveOffApprovalGroupId, CommonStatus status, Long id);
	
	Optional<WaiveOffApprovalTypes> findByWaiveOffTypeIdAndWaiveOffApprovalGroupIdAndStatus(Long waiveOffTypeId, Long waiveOffApprovalGroupId, CommonStatus status);
	
}

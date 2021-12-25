package com.fusionx.lending.transaction.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.WaiveOffApprovalGroup;
import com.fusionx.lending.transaction.enums.CommonStatus;

@Repository
public interface WaiveOffApprovalGroupRepository extends JpaRepository<WaiveOffApprovalGroup, Long> {
	
	List<WaiveOffApprovalGroup> findByStatus(CommonStatus status);
	
	List<WaiveOffApprovalGroup> findByNameContaining(String name);
	
	List<WaiveOffApprovalGroup> findByCode(String code);
	
	Optional<WaiveOffApprovalGroup> findByCodeAndStatus(String code,CommonStatus status);
	
	Optional<WaiveOffApprovalGroup> findByCodeAndStatusAndIdNotIn(String code, CommonStatus status, Long id);
	
}

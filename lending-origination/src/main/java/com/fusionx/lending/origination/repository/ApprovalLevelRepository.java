package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ApprovalLevel;


@Repository
public interface ApprovalLevelRepository extends JpaRepository<ApprovalLevel, Long>{
	
	List<ApprovalLevel> findByStatus(String status);
	
	Optional<ApprovalLevel> findByCode(String code);
	
	Optional<ApprovalLevel> findBySequence(Long sequence);

	List<ApprovalLevel> findByName(String name);

	Optional<ApprovalLevel> findByCodeAndIdNotIn(String code, Long id);

	Optional<ApprovalLevel> findBySequenceAndIdNotIn(Long sequence, Long id);

	Optional<ApprovalLevel> findByIdAndNameAndStatus(long parseLong, String approvalLevel, String string);

}

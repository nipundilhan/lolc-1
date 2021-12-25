package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ApprovalLevelDaGroupMapping;

@Repository
public interface ApprovalLevelDaGroupMapRepository extends JpaRepository<ApprovalLevelDaGroupMapping, Long>{

	List<ApprovalLevelDaGroupMapping> findByStatus(String status);

	List<ApprovalLevelDaGroupMapping> findByApprovalLevelId(Long levelId);

}

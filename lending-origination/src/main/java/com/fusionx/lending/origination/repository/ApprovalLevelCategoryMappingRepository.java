package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.ApprovalLevelCategoryMapping;

@Repository
public interface ApprovalLevelCategoryMappingRepository extends JpaRepository<ApprovalLevelCategoryMapping, Long>{

	List<ApprovalLevelCategoryMapping> findByStatus(String status);

	List<ApprovalLevelCategoryMapping> findByApprovalCategoryId(Long categoryId);

}

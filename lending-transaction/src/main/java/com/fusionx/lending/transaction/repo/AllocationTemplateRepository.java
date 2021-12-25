package com.fusionx.lending.transaction.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.transaction.domain.AllocationTemplate;
import com.fusionx.lending.transaction.enums.CommonStatus;

@Repository
public interface AllocationTemplateRepository extends JpaRepository<AllocationTemplate, Long>{

	public List<AllocationTemplate> findByStatus(CommonStatus status);

	public Optional<AllocationTemplate> findByCode(String code);
	
	public List<AllocationTemplate> findByNameContaining(String desc);
	
	public Optional<AllocationTemplate> findByIdAndStatus(Long id, CommonStatus status);
}

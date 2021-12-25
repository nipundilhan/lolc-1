package com.fusionx.lending.origination.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;
import com.fusionx.lending.origination.enums.LeadStatus;

@Repository
public interface LeadInfoRepository extends JpaRepository<LeadInfo, Long> {


	public List<LeadInfo> findByStatus(String status);

	List<LeadInfo> findByStatus(CommonStatus status);

	
	public boolean existsById(Long id);
	
	public List<LeadInfo> findByLeadStatus(LeadStatus status);

	public List<LeadInfo> findByBranchId(Long branchId);
	
	public List<LeadInfo> findByCreatedDateBetween(Date from, Date to);
	
	Optional<LeadInfo> findByIdAndStatus(Long id,String status);
	
}

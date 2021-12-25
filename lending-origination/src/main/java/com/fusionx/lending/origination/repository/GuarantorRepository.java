package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.Guarantor;
import com.fusionx.lending.origination.domain.LeadInfo;

@Repository
public interface GuarantorRepository extends JpaRepository<Guarantor, Long>{

	public List<Guarantor> findByLeadIdId(Long leadId);

	public Optional<Guarantor> findByIdAndStatus(Long id, String status);

	public Optional<Guarantor> findByPendingGuarantorId(Long penSupId);

	public Optional<Guarantor> findByPerIdAndLeadId(String perId, LeadInfo lead);


	public Optional<Guarantor> findByPenPerIdAndLeadId(String penPerId, LeadInfo lead);


	public Optional<Guarantor> findByPenPerIdAndLeadIdAndIdNotIn(String penPerId, LeadInfo lead, Long id);

	public Optional<Guarantor> findByPerIdAndLeadIdAndIdNotIn(String perId, LeadInfo lead, Long id);

	public Optional<Guarantor> findByIdAndLeadId(Long id, LeadInfo lead);
	
	//public Optional<Guarantor> findByLeadIdId(Long leadId);
	
	
}

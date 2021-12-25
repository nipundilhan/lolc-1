package com.fusionx.lending.origination.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.LeadInfo;
import com.fusionx.lending.origination.enums.CommonStatus;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	public Optional<Customer> findByIdAndStatus(Long id, String status);

	public List<Customer> findByLeadId(Long leadId);	
	
	public List<Customer> findByLeadIdOrderByLeadIdDesc(Long leadId);	

	public Optional<Customer> findByIdAndStatus(Long parseLong, CommonStatus active);

	
	public Optional<Customer> findByCustomerIdAndStatus(Long custId, String status);

	public Optional<Customer> findByPendingCustomerId(Long penCusId);

	public Optional<Customer> findByPerIdAndLead(String string, LeadInfo lead);

	public Optional<Customer> findByPenPerIdAndLead(String parseLong, LeadInfo lead);

	public Optional<Customer> findByIdAndLead(Long id, LeadInfo lead);

	public Optional<Customer> findByPerIdAndLeadAndIdNotIn(String perId, LeadInfo lead, Long id);

	public Optional<Customer> findByPenPerIdAndLeadAndIdNotIn(String penPerId, LeadInfo lead, Long id);
	
	public List<Customer> findByFullNameContaining(String name);	
	
	public Optional<Customer> findByBrNumber(String brNumber);
	
	public Optional<Customer> findByCustomerId(Long customerId);
	
	public Optional<Customer> findByCustomerReference(String customerId);
	


}

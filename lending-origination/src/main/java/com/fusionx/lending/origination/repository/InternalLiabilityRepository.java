package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.InternalLiability;

public interface InternalLiabilityRepository  extends JpaRepository<InternalLiability, Long>{

	
	List<InternalLiability> findAllByCustomer(Customer customer);
	
	public List<InternalLiability> findAllByStatus(String status);
}

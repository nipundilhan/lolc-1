package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionx.lending.origination.domain.Customer;
import com.fusionx.lending.origination.domain.ExternalLiability;
import com.fusionx.lending.origination.enums.CommonStatus;

public interface ExternalLiabilityRepository extends JpaRepository<ExternalLiability, Long>{
	
	List<ExternalLiability> findAllByCustomer(Customer customer);
	
	public List<ExternalLiability> findAllByStatus(CommonStatus status);

}

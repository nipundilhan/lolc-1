package com.fusionx.lending.origination.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.LinkedPerson;
@Repository
public interface LinkedPersonRepository extends JpaRepository<LinkedPerson, Long> {
	
	Optional<LinkedPerson> findByCustomerIdAndStatus(Long custId, String string);

	public Optional<LinkedPerson> findByIdAndStatus(Long parseLong, String string);
	
	public List<LinkedPerson> findByCustomerId(Long customerId);

	public Optional<LinkedPerson> findByIdAndCustomerId(Long relationId, Long customerid);


}

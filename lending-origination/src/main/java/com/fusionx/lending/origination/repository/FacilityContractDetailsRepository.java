package com.fusionx.lending.origination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fusionx.lending.origination.domain.FacilityContractDetails;

@Repository
public interface FacilityContractDetailsRepository extends JpaRepository<FacilityContractDetails, Long> {

	List<FacilityContractDetails> findByleadInfoId(Long id);

}
